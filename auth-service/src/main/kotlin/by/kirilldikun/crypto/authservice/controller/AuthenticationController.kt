package by.kirilldikun.crypto.authservice.controller

import by.kirilldikun.crypto.authservice.dto.AuthenticationDto
import by.kirilldikun.crypto.authservice.service.AuthenticationService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/auth")
class AuthenticationController(
    val authenticationService: AuthenticationService
) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody @Valid authenticationDto: AuthenticationDto): String =
        authenticationService.register(authenticationDto)

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    fun authenticate(@RequestBody @Valid authenticationDto: AuthenticationDto): String =
        authenticationService.authenticate(authenticationDto)
}