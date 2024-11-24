package by.kirilldikun.crypto.externalapiservice.alphavantage.feign

import by.kirilldikun.crypto.externalapiservice.alphavantage.dto.NewsResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "alphavantageFeignClient", url = "\${service.alphavantage.url}")
interface AlphavantageFeignClient {

    @GetMapping
    fun findAllNews(
        @RequestParam function: String = "NEWS_SENTIMENT",
        @RequestParam tickers: List<String> = listOf("COIN"),
        @RequestParam apikey: String
    ): NewsResponse
}