package by.kirilldikun.crypto.notificationservice.service

import by.kirilldikun.crypto.commons.dto.EmailDto

interface EmailService {

    fun send(emailDto: EmailDto)
}