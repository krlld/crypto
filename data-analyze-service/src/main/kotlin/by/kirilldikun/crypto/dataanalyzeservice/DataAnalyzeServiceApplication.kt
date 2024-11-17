package by.kirilldikun.crypto.dataanalyzeservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport

@EnableSpringDataWebSupport(
    pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
)
@SpringBootApplication
class DataAnalyzeServiceApplication

fun main(args: Array<String>) {
    runApplication<DataAnalyzeServiceApplication>(*args)
}