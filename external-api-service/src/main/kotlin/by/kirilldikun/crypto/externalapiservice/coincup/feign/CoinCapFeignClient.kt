package by.kirilldikun.crypto.externalapiservice.coincup.feign

import by.kirilldikun.crypto.externalapiservice.coincup.dto.CurrenciesResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "coinCapFeignClient", url = "\${service.coin-cap.url}")
interface CoinCapFeignClient {

    @GetMapping("/assets")
    fun getCurrencies(@RequestParam(required = false) ids: List<String>? = null): CurrenciesResponse
}