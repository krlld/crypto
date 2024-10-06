package by.kirilldikun.crypto.commons.config

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val id: Long,
    private val username: String,
    private val password: String,
    private val authorities: Set<GrantedAuthority>
) : UserDetails {

    fun getId(): Long = id

    override fun getAuthorities() = authorities

    override fun getPassword(): String = password

    override fun getUsername(): String = username
}