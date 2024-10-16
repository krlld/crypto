package by.kirilldikun.crypto.commons.producer

import by.kirilldikun.crypto.commons.dto.EmailDto
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
@ConditionalOnBean(KafkaTemplate::class)
class EmailSendingProducer(
    val kafkaTemplate: KafkaTemplate<String, Any>
) {

    fun send(emailDto: EmailDto) {
        kafkaTemplate.send("email-sending-topic", emailDto)
    }
}