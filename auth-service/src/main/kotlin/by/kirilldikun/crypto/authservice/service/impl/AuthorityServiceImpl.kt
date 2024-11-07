package by.kirilldikun.crypto.authservice.service.impl

import by.kirilldikun.crypto.commons.dto.AuthorityDto
import by.kirilldikun.crypto.authservice.mapper.AuthorityMapper
import by.kirilldikun.crypto.authservice.repository.AuthorityRepository
import by.kirilldikun.crypto.authservice.service.AuthorityService
import by.kirilldikun.crypto.commons.service.UserService
import by.kirilldikun.crypto.commons.util.TokenHelper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorityServiceImpl(
    val authorityRepository: AuthorityRepository,
    val userService: UserService,
    val authorityMapper: AuthorityMapper,
    val tokenHelper: TokenHelper
) : AuthorityService {

    @Transactional(readOnly = true)
    override fun findAll(pageable: Pageable): Page<AuthorityDto> {
        return authorityRepository.findAll(pageable)
            .map { authorityMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    override fun findAllMyAuthorities(): List<AuthorityDto> {
        val userId = tokenHelper.getUserId()
        val user = userService.findAllByIds(listOf(userId)).first()
        return user.roles!!.flatMap { it.authorities!!.toList() }
    }
}