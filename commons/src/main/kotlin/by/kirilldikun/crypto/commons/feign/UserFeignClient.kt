package by.kirilldikun.crypto.commons.feign

import by.kirilldikun.crypto.commons.config.CustomUserDetails
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@ConditionalOnProperty("auth.url")
@FeignClient(name = "userFeignClient", url = "\${auth.url}")
interface UserFeignClient {

    @GetMapping("/users")
    fun findByEmail(@RequestParam email: String): CustomUserDetails
}