package by.kirilldikun.crypto.notificationservice.config

import by.kirilldikun.crypto.commons.config.KafkaConsumerFactory
import by.kirilldikun.crypto.commons.dto.EmailDto
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory

@Configuration
class KafkaConsumerConfiguration(
    val kafkaConsumerFactory: KafkaConsumerFactory
) {

    @Bean
    fun emailDtoKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, EmailDto> {
        return kafkaConsumerFactory.initFactory(EmailDto::class.java)
    }
}