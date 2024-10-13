package by.kirilldikun.crypto.commons.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
@ConditionalOnProperty("spring.kafka.bootstrap-servers")
class KafkaTopicConfiguration {

    @Bean
    fun reportCreationTopic(): NewTopic {
        return TopicBuilder.name("report-creation-topic").build()
    }

    @Bean
    fun reportSendingTopic(): NewTopic {
        return TopicBuilder.name("report-sending-topic").build()
    }
}