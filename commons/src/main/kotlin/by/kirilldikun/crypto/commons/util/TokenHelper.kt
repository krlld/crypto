package by.kirilldikun.crypto.commons.util

import by.kirilldikun.crypto.commons.config.CustomUserDetails
import by.kirilldikun.crypto.commons.exception.UnauthorizedException
import java.lang.ClassCastException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class TokenHelper {

    fun getUserId(): Long {
        val authentication = SecurityContextHolder.getContext().authentication
        val principal = try {
            authentication.principal as CustomUserDetails
        } catch (e: ClassCastException) {
            throw UnauthorizedException("Error while getting authorization")
        }
        return principal.getId()
    }
}