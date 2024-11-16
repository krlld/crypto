package by.kirilldikun.crypto.authservice.service

import by.kirilldikun.crypto.commons.dto.UserDto

interface UserService {

    fun findAllByIds(userIds: List<Long>): List<UserDto>

    fun aboutMe(): UserDto

    fun save(userDto: UserDto): UserDto
}