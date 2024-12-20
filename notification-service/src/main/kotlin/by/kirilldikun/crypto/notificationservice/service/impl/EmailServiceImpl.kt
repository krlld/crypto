package by.kirilldikun.crypto.notificationservice.service.impl

import by.kirilldikun.crypto.commons.dto.EmailDto
import by.kirilldikun.crypto.commons.service.UserService
import by.kirilldikun.crypto.notificationservice.service.EmailService
import by.kirilldikun.crypto.notificationservice.service.FileService
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl(
    val javaMailSender: JavaMailSender,
    val userService: UserService,
    val fileService: FileService
) : EmailService {

    // TODO: refactor using cache for users
    override fun send(emailDto: EmailDto) {
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        val user = userService.findAllByIds(listOf(emailDto.toId)).first()

        helper.setTo(user.email)
        helper.setSubject(emailDto.subject)
        helper.setText(emailDto.text, true)

        if (emailDto.attachments.isNotEmpty()) {
            emailDto.attachments.forEach {
                val resource = fileService.downloadFile(it)
                helper.addAttachment(resource.filename!!, resource)
            }
        }

        javaMailSender.send(message)
    }
}