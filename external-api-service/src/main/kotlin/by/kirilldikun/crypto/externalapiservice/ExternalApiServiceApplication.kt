package by.kirilldikun.crypto.externalapiservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.web.config.EnableSpringDataWebSupport

@EnableFeignClients
@EnableSpringDataWebSupport(
    pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
)
@SpringBootApplication
class ExternalApiServiceApplication

fun main(args: Array<String>) {
    runApplication<ExternalApiServiceApplication>(*args)
}
