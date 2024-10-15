package by.kirilldikun.crypto.externalapiservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class ExternalApiServiceApplication

fun main(args: Array<String>) {
	runApplication<ExternalApiServiceApplication>(*args)
}
