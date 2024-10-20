package by.kirilldikun.crypto.authservice.controller

import by.kirilldikun.crypto.commons.dto.UserDto
import by.kirilldikun.crypto.commons.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/users")
class UserController(
    val userDetailsService: UserDetailsService,
    val userService: UserService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findByEmail(@RequestParam email: String): UserDetails {
        return userDetailsService.loadUserByUsername(email)
    }

    @GetMapping("/users-by-ids")
    @ResponseStatus(HttpStatus.OK)
    fun findAllByIds(@RequestParam ids: List<Long>): List<UserDto> {
        return userService.findAllByIds(ids)
    }
}