package by.kirilldikun.crypto.dataanalyzeservice.consumer

import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import by.kirilldikun.crypto.dataanalyzeservice.service.ReportService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ReportSendingConsumer(
    val reportService: ReportService
) {

    @KafkaListener(
        topics = ["report-sending-topic"],
        groupId = "data-analyze-service",
        containerFactory = "reportDtoKafkaListenerContainerFactory"
    )
    fun consume(report: ReportDto) {
        reportService.save(report)
    }
}