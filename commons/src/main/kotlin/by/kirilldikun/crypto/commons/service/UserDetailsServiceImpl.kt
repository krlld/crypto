package by.kirilldikun.crypto.commons.service

import by.kirilldikun.crypto.commons.config.CustomUserDetails
import by.kirilldikun.crypto.commons.feign.UserFeignClient
import by.kirilldikun.crypto.commons.util.ExceptionParser
import feign.FeignException
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@ConditionalOnMissingBean(UserDetailsService::class)
class UserDetailsServiceImpl(
    val userFeignClient: UserFeignClient,
    val exceptionParser: ExceptionParser
) : UserDetailsService {

    override fun loadUserByUsername(username: String): CustomUserDetails {
        try {
            return userFeignClient.findByEmail(username)
        } catch (e: FeignException.NotFound) {
            val apiErrorResponse = exceptionParser.toApiError(e)
            throw UsernameNotFoundException(apiErrorResponse.message)
        }
    }
}