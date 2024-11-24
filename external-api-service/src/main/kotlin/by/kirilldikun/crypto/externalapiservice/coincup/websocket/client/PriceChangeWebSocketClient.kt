package by.kirilldikun.crypto.externalapiservice.coincup.websocket.client

import by.kirilldikun.crypto.externalapiservice.coincup.websocket.handler.PriceChangeWebSocketHandler
import jakarta.annotation.PostConstruct
import jakarta.websocket.ContainerProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.socket.client.standard.StandardWebSocketClient


@Component
class PriceChangeWebSocketClient(
    @Value("\${service.coincap.web-socket.prices.url}")
    val webSocketUrl: String,
    val priceChangeWebSocketHandler: PriceChangeWebSocketHandler
) {

    @PostConstruct
    fun connect() {
        val container = ContainerProvider.getWebSocketContainer()

        container.defaultMaxBinaryMessageBufferSize = 1024 * 1024
        container.defaultMaxTextMessageBufferSize = 1024 * 1024

        val webSocketClient = StandardWebSocketClient(container)

        webSocketClient.execute(priceChangeWebSocketHandler, "$webSocketUrl?assets=ALL")
    }
}