package by.kirilldikun.crypto.authservice.service

import by.kirilldikun.crypto.authservice.dto.AuthenticationDto

interface AuthenticationService {

    fun register(authenticationDto: AuthenticationDto): String

    fun authenticate(authenticationDto: AuthenticationDto): String
}