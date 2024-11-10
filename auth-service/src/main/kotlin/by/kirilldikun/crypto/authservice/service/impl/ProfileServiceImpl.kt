package by.kirilldikun.crypto.authservice.service.impl

import by.kirilldikun.crypto.authservice.dto.ProfileDto
import by.kirilldikun.crypto.authservice.mapper.ProfileMapper
import by.kirilldikun.crypto.authservice.repository.UserRepository
import by.kirilldikun.crypto.authservice.service.ProfileService
import by.kirilldikun.crypto.commons.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProfileServiceImpl(
    val userRepository: UserRepository,
    val profileMapper: ProfileMapper
) : ProfileService {

    @Transactional
    override fun update(userId: Long, profileDto: ProfileDto): ProfileDto {
        val foundUser = userRepository.findById(userId)
            .orElseThrow { NotFoundException("User with id: $userId not found") }
        val user = profileMapper.toEntity(foundUser, profileDto)
        val savedUser = userRepository.save(user)
        return profileMapper.toDto(savedUser)
    }
}