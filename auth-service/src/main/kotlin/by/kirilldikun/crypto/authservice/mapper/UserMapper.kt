package by.kirilldikun.crypto.authservice.mapper

import by.kirilldikun.crypto.authservice.model.Role
import by.kirilldikun.crypto.authservice.model.User
import by.kirilldikun.crypto.commons.dto.UserDto
import by.kirilldikun.crypto.commons.mapper.Mapper
import org.springframework.stereotype.Component

@Component
class UserMapper(
    val roleMapper: RoleMapper
) : Mapper<User, UserDto> {

    override fun toDto(e: User): UserDto {
        return UserDto(
            id = e.id!!,
            email = e.email,
            password = e.password,
            name = e.name,
            lastname = e.lastname,
            avatarId = e.avatarId,
            roles = e.roles.map { roleMapper.toDto(it) }.toSet()
        )
    }

    override fun toEntity(d: UserDto): User {
        return User(
            id = d.id,
            email = d.email,
            password = d.password,
            name = d.name,
            lastname = d.lastname,
            avatarId = d.avatarId,
            roles = d.roleIds!!.map { Role(id = it) }.toMutableSet()
        )
    }
}