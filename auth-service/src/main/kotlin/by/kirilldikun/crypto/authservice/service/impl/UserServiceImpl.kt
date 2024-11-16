package by.kirilldikun.crypto.authservice.service.impl

import by.kirilldikun.crypto.authservice.mapper.UserMapper
import by.kirilldikun.crypto.authservice.repository.UserRepository
import by.kirilldikun.crypto.authservice.service.UserService
import by.kirilldikun.crypto.commons.dto.UserDto
import by.kirilldikun.crypto.commons.exception.NotFoundException
import by.kirilldikun.crypto.commons.util.TokenHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val userMapper: UserMapper,
    val tokenHelper: TokenHelper
) : UserService {

    @Transactional(readOnly = true)
    override fun findAllByIds(userIds: List<Long>): List<UserDto> {
        return userRepository.findAllById(userIds)
            .map { userMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    override fun aboutMe(): UserDto {
        val userId = tokenHelper.getUserId()
        return userRepository.findById(userId)
            .map { userMapper.toDto(it) }
            .orElseThrow { NotFoundException("User with id: $userId not found") }
    }

    @Transactional
    override fun save(userDto: UserDto): UserDto {
        val user = userMapper.toEntity(userDto)
        val savedUser = userRepository.save(user)
        return userMapper.toDto(savedUser)
    }
}