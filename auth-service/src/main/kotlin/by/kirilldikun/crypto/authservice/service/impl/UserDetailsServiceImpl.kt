package by.kirilldikun.crypto.authservice.service.impl

import by.kirilldikun.crypto.authservice.repository.UserRepository
import by.kirilldikun.crypto.commons.config.CustomUserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(
    val userRepository: UserRepository
) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): CustomUserDetails {
        val user = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User with email $username not found")
        return CustomUserDetails(
            id = user.id!!,
            username = user.email,
            password = user.password,
            authorities = emptySet()
        )
    }
}