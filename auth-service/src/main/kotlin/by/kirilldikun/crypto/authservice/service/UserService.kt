package by.kirilldikun.crypto.authservice.service

import by.kirilldikun.crypto.commons.dto.UserDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {

    fun findAll(pageable: Pageable): Page<UserDto>

    fun findAllByIds(userIds: List<Long>): List<UserDto>

    fun aboutMe(): UserDto

    fun save(userDto: UserDto): UserDto
}