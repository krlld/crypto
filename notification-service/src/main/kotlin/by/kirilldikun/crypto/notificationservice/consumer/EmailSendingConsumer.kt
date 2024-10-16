package by.kirilldikun.crypto.notificationservice.consumer

import by.kirilldikun.crypto.commons.dto.EmailDto
import by.kirilldikun.crypto.notificationservice.service.EmailService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class EmailSendingConsumer(
    val emailService: EmailService
) {

    @KafkaListener(
        topics = ["email-sending-topic"],
        groupId = "notification-service",
        containerFactory = "emailDtoKafkaListenerContainerFactory"
    )
    fun consume(emailDto: EmailDto) {
        emailService.send(emailDto)
    }
}