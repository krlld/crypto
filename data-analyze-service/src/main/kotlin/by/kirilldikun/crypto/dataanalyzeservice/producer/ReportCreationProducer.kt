package by.kirilldikun.crypto.dataanalyzeservice.producer

import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ReportCreationProducer(
    @Value("\${kafka.report-creation-topic.name}")
    val topicName: String,
    val kafkaTemplate: KafkaTemplate<String, Any>
) {

    fun send(reportDto: ReportDto) {
        kafkaTemplate.send(topicName, reportDto)
    }
}