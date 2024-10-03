package by.kirilldikun.crypto.dataanalyzeservice.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaTopicConfiguration(
    @Value("\${kafka.report-creation-topic.name}")
    val reportCreationTopicName: String
) {

    @Bean
    fun newTopic(): NewTopic {
        return TopicBuilder.name(reportCreationTopicName).build()
    }
}