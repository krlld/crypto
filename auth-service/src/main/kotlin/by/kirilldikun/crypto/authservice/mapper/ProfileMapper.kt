package by.kirilldikun.crypto.authservice.mapper

import by.kirilldikun.crypto.authservice.dto.ProfileDto
import by.kirilldikun.crypto.authservice.model.User
import org.springframework.stereotype.Component

@Component
class ProfileMapper {

    fun toDto(e: User): ProfileDto {
        return ProfileDto(
            name = e.name,
            lastname = e.lastname,
            avatarId = e.avatarId
        )
    }

    fun toEntity(user: User, profile: ProfileDto): User {
        return User(
            id = user.id,
            email = user.email,
            password = user.password,
            name = profile.name,
            lastname = profile.lastname,
            avatarId = profile.avatarId,
            roles = user.roles
        )
    }
}