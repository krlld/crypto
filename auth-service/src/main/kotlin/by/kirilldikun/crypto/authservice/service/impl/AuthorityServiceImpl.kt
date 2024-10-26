package by.kirilldikun.crypto.authservice.service.impl

import by.kirilldikun.crypto.commons.dto.AuthorityDto
import by.kirilldikun.crypto.authservice.mapper.AuthorityMapper
import by.kirilldikun.crypto.authservice.repository.AuthorityRepository
import by.kirilldikun.crypto.authservice.service.AuthorityService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorityServiceImpl(
    val authorityRepository: AuthorityRepository,
    val authorityMapper: AuthorityMapper
) : AuthorityService {

    @Transactional(readOnly = true)
    override fun findAll(pageable: Pageable): Page<AuthorityDto> {
        return authorityRepository.findAll(pageable)
            .map { authorityMapper.toDto(it) }
    }
}