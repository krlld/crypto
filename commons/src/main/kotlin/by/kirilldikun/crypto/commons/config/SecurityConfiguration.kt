package by.kirilldikun.crypto.commons.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(
    //val defaultAuthenticationTokenFilter: DefaultAuthenticationTokenFilter
) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .sessionManagement { configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            //.addFilterBefore(defaultAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}