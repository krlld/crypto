package by.kirilldikun.crypto.dataanalyzeservice.config

import by.kirilldikun.crypto.commons.config.KafkaConsumerFactory
import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory

@Configuration
class KafkaConsumerConfiguration(
    val kafkaConsumerFactory: KafkaConsumerFactory
) {

    @Bean
    fun reportDtoKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, ReportDto> {
        return kafkaConsumerFactory.initFactory(ReportDto::class.java)
    }
}