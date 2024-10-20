package by.kirilldikun.crypto.dataanalyzeservice.consumer

import by.kirilldikun.crypto.commons.service.EmailService
import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import by.kirilldikun.crypto.dataanalyzeservice.service.ReportService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ReportSendingConsumer(
    val reportService: ReportService,
    val emailService: EmailService
) {

    @KafkaListener(
        topics = ["report-sending-topic"],
        groupId = "data-analyze-service",
        containerFactory = "reportDtoKafkaListenerContainerFactory"
    )
    fun consume(report: ReportDto) {
        val savedReport = reportService.save(report)

        val notifications = emailService.createNotifications(
            userIds = listOf(savedReport.userId!!),
            key = "report.notification",
            bodyArgs = { mapOf("title" to savedReport.title) },
            attachments = listOf(savedReport.resultFileId!!)
        )
        emailService.send(notifications)
    }
}