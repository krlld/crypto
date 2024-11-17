package by.kirilldikun.crypto.authservice.service

import by.kirilldikun.crypto.authservice.dto.ProfileDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProfileService {

    fun findAll(pageable: Pageable): Page<ProfileDto>

    fun findAllByIds(userIds: List<Long>): List<ProfileDto>

    fun update(profileDto: ProfileDto): ProfileDto
}