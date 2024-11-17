package by.kirilldikun.crypto.commons.feign

import by.kirilldikun.crypto.commons.config.CustomUserDetails
import by.kirilldikun.crypto.commons.dto.UserDto
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@ConditionalOnProperty("service.auth.url")
@FeignClient(name = "userFeignClient", url = "\${service.auth.url}/users")
interface UserFeignClient {

    @GetMapping("/by-email")
    fun findByEmail(@RequestParam email: String): CustomUserDetails

    @GetMapping("/users-by-ids")
    fun findAllByIds(@RequestParam ids: List<Long>): List<UserDto>
}