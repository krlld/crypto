package by.kirilldikun.crypto.commons.config

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.openfeign.EnableFeignClients

@AutoConfiguration
@EnableFeignClients("by.kirilldikun.crypto.commons.feign")
@EnableConfigurationProperties(JwtProperties::class)
class CommonConfiguration {
}