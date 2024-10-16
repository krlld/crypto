package by.kirilldikun.crypto.commons.config

import by.kirilldikun.crypto.commons.exception.UnauthorizedException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
@ConditionalOnBean(UserDetailsService::class)
class JwtAuthenticationFilter(
    val jwtParser: JwtParser,
    val userDetailsService: UserDetailsService,
    val handlerExceptionResolver: HandlerExceptionResolver
) : OncePerRequestFilter() {

    companion object {
        const val BEARER_ = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authHeader.isNullOrEmpty() || !authHeader.startsWith(BEARER_)) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.substring(BEARER_.length)

        val username = try {
            jwtParser.extractUsername(token)
        } catch (e: MalformedJwtException) {
            handlerExceptionResolver.resolveException(request, response, null, UnauthorizedException("Invalid token"))
            return
        } catch (e: ExpiredJwtException) {
            handlerExceptionResolver.resolveException(request, response, null, UnauthorizedException("Expired token"))
            return
        }

        val userDetails = try {
            userDetailsService.loadUserByUsername(username)
        } catch (e: UsernameNotFoundException) {
            handlerExceptionResolver.resolveException(request, response, null, UnauthorizedException(e.message ?: ""))
            return
        }

        if (jwtParser.isTokenValid(token, userDetails)) {
            val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.authorities
            )
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
        }

        filterChain.doFilter(request, response)
    }
}