package by.kirilldikun.crypto.commons.service

import by.kirilldikun.crypto.commons.dto.EmailDto

interface EmailService {

    fun send(emailDtos: List<EmailDto>)

    fun createNotifications(
        userIds: List<Long>,
        key: String,
        titleArgs: () -> Map<String, String> = { mapOf() },
        bodyArgs: () -> Map<String, String> = { mapOf() },
        attachments: List<String> = emptyList()
    ): List<EmailDto>
}