package by.kirilldikun.crypto.externalapiservice.coincup.websocket.handler

import by.kirilldikun.crypto.commons.service.EmailService
import by.kirilldikun.crypto.externalapiservice.coincup.model.ComparisonType
import by.kirilldikun.crypto.externalapiservice.coincup.repository.SubscriptionToPriceRepository
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.math.BigDecimal
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class PriceChangeWebSocketHandler(
    val subscriptionToPriceRepository: SubscriptionToPriceRepository,
    val notificationService: EmailService,
    val objectMapper: ObjectMapper
) : TextWebSocketHandler() {


    // TODO: refactor with using cache
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val newPrices = objectMapper.readValue(message.payload, object : TypeReference<Map<String, BigDecimal>>() {})
        val subscriptionToPrices = subscriptionToPriceRepository.findAll()
        val results = subscriptionToPrices.filter {
            if (it.comparisonType == ComparisonType.GRATER_THAN) {
                (newPrices[it.currencyId] ?: BigDecimal.ZERO) > it.price
            } else {
                (newPrices[it.currencyId] ?: BigDecimal.ZERO) < it.price
            }
        }

        val notifications = results.map { subscriptionToPrice ->
            notificationService.createNotifications(
                userIds = listOf(subscriptionToPrice.userId),
                key = "price.subscription.success",
                bodyArgs = {
                    mapOf(
                        "currency" to subscriptionToPrice.currencyId,
                        "price" to subscriptionToPrice.price.toString()
                    )
                }
            )
        }.flatten()
        notificationService.send(notifications)

        subscriptionToPriceRepository.deleteAll(results)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        println("Connection closed: $status")
    }
}