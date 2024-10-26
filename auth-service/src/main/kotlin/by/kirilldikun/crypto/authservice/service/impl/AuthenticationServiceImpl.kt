package by.kirilldikun.crypto.authservice.service.impl

import by.kirilldikun.crypto.authservice.dto.AuthenticationDto
import by.kirilldikun.crypto.authservice.mapper.UserMapper
import by.kirilldikun.crypto.authservice.repository.UserRepository
import by.kirilldikun.crypto.authservice.service.AuthenticationService
import by.kirilldikun.crypto.commons.config.CustomUserDetails
import by.kirilldikun.crypto.commons.config.JwtParser
import by.kirilldikun.crypto.commons.dto.UserDto
import by.kirilldikun.crypto.commons.exception.ConflictException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthenticationServiceImpl(
    val userRepository: UserRepository,
    val userDetailsService: UserDetailsService,
    val userMapper: UserMapper,
    val jwtParser: JwtParser,
    val passwordEncoder: PasswordEncoder,
    val authenticationManager: AuthenticationManager
) : AuthenticationService {

    @Transactional
    override fun register(userDto: UserDto): UserDto {
        val foundUser = userRepository.findByEmail(userDto.email)
        if (foundUser != null) {
            throw ConflictException("User with email ${userDto.email} already exists")
        }

        val userWithEncodedPassword = userDto.copy(password = passwordEncoder.encode(userDto.password))
        val user = userMapper.toEntity(userWithEncodedPassword)
        userRepository.save(user)

        return userMapper.toDto(user)
    }

    @Transactional(readOnly = true)
    override fun authenticate(authenticationDto: AuthenticationDto): String {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationDto.email,
                authenticationDto.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authenticationDto.email) as CustomUserDetails
        return jwtParser.generateToken(user)
    }
}