package by.kirilldikun.crypto.notificationservice.service.impl

import by.kirilldikun.crypto.commons.dto.EmailDto
import by.kirilldikun.crypto.notificationservice.service.EmailService
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl(
    val javaMailSender: JavaMailSender
) : EmailService {

    override fun send(emailDto: EmailDto) {
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        helper.setTo(emailDto.to.toTypedArray())
        helper.setSubject(emailDto.subject)
        helper.setText(emailDto.text, true)

//        if (emailDto.attachments.isNotEmpty()) {
//            emailDto.attachments.forEach {
//                helper.addAttachment(it.first, it.second)
//            }
//        }

        javaMailSender.send(message)
    }
}