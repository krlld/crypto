package by.kirilldikun.crypto.dataanalyzeservice.producer

import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ReportCreationProducer(
    val kafkaTemplate: KafkaTemplate<String, Any>
) {

    fun send(reportDto: ReportDto) {
        kafkaTemplate.send("report-creation-topic", reportDto)
    }
}