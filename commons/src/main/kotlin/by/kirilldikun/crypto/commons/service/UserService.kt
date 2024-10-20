package by.kirilldikun.crypto.commons.service

import by.kirilldikun.crypto.commons.dto.UserDto

interface UserService {

    fun findAllByIds(userIds: List<Long>): List<UserDto>
}