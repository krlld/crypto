package by.kirilldikun.crypto.dataanalyzeservice.consumer

import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ReportCreationConsumer {

    @KafkaListener(
        topics = ["\${kafka.report-creation-topic.name}"],
        groupId = "data-analyze-service"
    )
    fun consume(reportDto: ReportDto) {
        println("$reportDto!!!!!!!!")
    }
}