package by.kirilldikun.crypto.authservice.service

import by.kirilldikun.crypto.authservice.dto.AuthenticationDto
import by.kirilldikun.crypto.commons.dto.UserDto

interface AuthenticationService {

    fun register(userDto: UserDto): UserDto

    fun authenticate(authenticationDto: AuthenticationDto): String
}