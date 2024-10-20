package by.kirilldikun.crypto.commons.service.impl

import by.kirilldikun.crypto.commons.dto.EmailDto
import by.kirilldikun.crypto.commons.producer.EmailSendingProducer
import by.kirilldikun.crypto.commons.service.EmailService
import by.kirilldikun.crypto.commons.util.MessageLocaleHelper
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Service

@Service
@ConditionalOnBean(EmailSendingProducer::class)
class EmailServiceImpl(
    val emailSendingProducer: EmailSendingProducer,
    val messageLocaleHelper: MessageLocaleHelper
) : EmailService {

    override fun send(emailDtos: List<EmailDto>) {
        emailDtos.forEach { emailSendingProducer.send(it) }
    }

    override fun createNotifications(
        userIds: List<Long>,
        key: String,
        titleArgs: () -> Map<String, String>,
        bodyArgs: () -> Map<String, String>
    ): List<EmailDto> {
        return userIds.map {
            EmailDto(
                toId = it,
                subject = messageLocaleHelper.getLocalizedMessage(
                    key = "$key.title",
                    args = titleArgs()
                ),
                text = messageLocaleHelper.getLocalizedMessage(
                    key = "$key.body",
                    args = bodyArgs()
                )
            )
        }
    }
}