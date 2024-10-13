package by.kirilldikun.crypto.commons.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty("spring.kafka.bootstrap-servers")
class KafkaConsumerFactory(
    @Value("\${spring.kafka.bootstrap-servers}")
    val bootstrapServers: String
) {

    private fun consumerConfig(): Map<String, Any> {
        return mapOf(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers)
    }

    private fun <T> typeConsumerFactory(clazz: Class<T>): ConsumerFactory<String, T> {
        return DefaultKafkaConsumerFactory(
            consumerConfig(),
            StringDeserializer(),
            JsonDeserializer(clazz)
        )
    }

    fun <T> initFactory(clazz: Class<T>): ConcurrentKafkaListenerContainerFactory<String, T> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, T>()
        factory.consumerFactory = typeConsumerFactory(clazz)
        return factory
    }
}