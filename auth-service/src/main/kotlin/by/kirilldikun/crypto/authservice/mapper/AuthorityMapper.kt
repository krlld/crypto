package by.kirilldikun.crypto.authservice.mapper

import by.kirilldikun.crypto.commons.dto.AuthorityDto
import by.kirilldikun.crypto.authservice.model.Authority
import by.kirilldikun.crypto.commons.mapper.Mapper
import org.springframework.stereotype.Component

@Component
class AuthorityMapper : Mapper<Authority, AuthorityDto> {

    override fun toDto(e: Authority): AuthorityDto {
        return AuthorityDto(
            id = e.id,
            name = e.name
        )
    }

    override fun toEntity(d: AuthorityDto): Authority {
        return Authority(
            id = d.id,
            name = d.name
        )
    }
}