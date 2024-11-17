package by.kirilldikun.crypto.authservice.service.impl

import by.kirilldikun.crypto.authservice.dto.ProfileDto
import by.kirilldikun.crypto.authservice.mapper.ProfileMapper
import by.kirilldikun.crypto.authservice.repository.UserRepository
import by.kirilldikun.crypto.authservice.service.ProfileService
import by.kirilldikun.crypto.commons.exception.NotFoundException
import by.kirilldikun.crypto.commons.util.TokenHelper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProfileServiceImpl(
    val userRepository: UserRepository,
    val profileMapper: ProfileMapper,
    val tokenHelper: TokenHelper
) : ProfileService {

    @Transactional(readOnly = true)
    override fun findAll(pageable: Pageable): Page<ProfileDto> {
        return userRepository.findAll(pageable)
            .map { profileMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    override fun findAllByIds(userIds: List<Long>): List<ProfileDto> {
        return userRepository.findAllById(userIds)
            .map { profileMapper.toDto(it) }
    }

    @Transactional
    override fun update(profileDto: ProfileDto): ProfileDto {
        val userId = tokenHelper.getUserId()
        val foundUser = userRepository.findById(userId)
            .orElseThrow { NotFoundException("User with id: $userId not found") }
        val user = profileMapper.toEntity(foundUser, profileDto)
        val savedUser = userRepository.save(user)
        return profileMapper.toDto(savedUser)
    }
}