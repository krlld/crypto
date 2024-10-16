package by.kirilldikun.crypto.commons.service.impl

import by.kirilldikun.crypto.commons.dto.EmailDto
import by.kirilldikun.crypto.commons.producer.EmailSendingProducer
import by.kirilldikun.crypto.commons.service.EmailService
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Service

@Service
@ConditionalOnBean(EmailSendingProducer::class)
class EmailServiceImpl(
    val emailSendingProducer: EmailSendingProducer
) : EmailService {

    override fun send(emailDto: EmailDto) {
        emailSendingProducer.send(emailDto)
    }
}