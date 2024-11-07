package by.kirilldikun.crypto.authservice.service

import by.kirilldikun.crypto.commons.dto.AuthorityDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AuthorityService {

    fun findAll(pageable: Pageable): Page<AuthorityDto>

    fun findAllMyAuthorities(): List<AuthorityDto>
}