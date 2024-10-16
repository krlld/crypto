package by.kirilldikun.crypto.authservice.service.impl

import by.kirilldikun.crypto.authservice.dto.AuthenticationDto
import by.kirilldikun.crypto.authservice.model.User
import by.kirilldikun.crypto.authservice.repository.UserRepository
import by.kirilldikun.crypto.authservice.service.AuthenticationService
import by.kirilldikun.crypto.commons.config.CustomUserDetails
import by.kirilldikun.crypto.commons.config.JwtParser
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
    val jwtParser: JwtParser,
    val passwordEncoder: PasswordEncoder,
    val authenticationManager: AuthenticationManager
) : AuthenticationService {

    @Transactional
    override fun register(authenticationDto: AuthenticationDto): String {
        val foundUser = userRepository.findByEmail(authenticationDto.email)
        if (foundUser != null) {
            throw ConflictException("User with email ${authenticationDto.email} already exists")
        }
        val newUser = User(
            email = authenticationDto.email,
            password = passwordEncoder.encode(authenticationDto.password)
        )
        userRepository.save(newUser)

        return jwtParser.generateToken(
            CustomUserDetails(
                id = newUser.id!!,
                username = newUser.email,
                password = newUser.password,
                authorities = emptySet()
            )
        )
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