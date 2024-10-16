package by.kirilldikun.crypto.commons.service

import by.kirilldikun.crypto.commons.dto.EmailDto

interface EmailService {

    fun send(emailDto: EmailDto)
}