package by.kirilldikun.crypto.authservice.service.impl

import by.kirilldikun.crypto.authservice.mapper.UserMapper
import by.kirilldikun.crypto.authservice.repository.UserRepository
import by.kirilldikun.crypto.commons.dto.UserDto
import by.kirilldikun.crypto.commons.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val userMapper: UserMapper
) : UserService {

    @Transactional(readOnly = true)
    override fun findAllByIds(userIds: List<Long>): List<UserDto> {
        return userRepository.findAllById(userIds)
            .map { userMapper.toDto(it) }
    }
}