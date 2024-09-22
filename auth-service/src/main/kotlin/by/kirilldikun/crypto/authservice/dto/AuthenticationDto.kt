package by.kirilldikun.crypto.authservice.dto

import jakarta.validation.constraints.Email

data class AuthenticationDto(

    @field:Email
    val email: String,

    val password: String
)