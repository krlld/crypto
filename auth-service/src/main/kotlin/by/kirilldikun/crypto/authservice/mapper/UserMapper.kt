package by.kirilldikun.crypto.authservice.mapper

import by.kirilldikun.crypto.authservice.model.User
import by.kirilldikun.crypto.commons.dto.UserDto
import by.kirilldikun.crypto.commons.mapper.Mapper
import org.springframework.stereotype.Component

@Component
class UserMapper : Mapper<User, UserDto> {

    override fun toDto(e: User): UserDto {
        return UserDto(
            id = e.id!!,
            email = e.email
        )
    }

    override fun toEntity(d: UserDto): User {
        TODO("Not yet implemented")
    }
}