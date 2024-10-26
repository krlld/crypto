package by.kirilldikun.crypto.authservice.mapper

import by.kirilldikun.crypto.commons.dto.RoleDto
import by.kirilldikun.crypto.authservice.model.Authority
import by.kirilldikun.crypto.authservice.model.Role
import by.kirilldikun.crypto.commons.mapper.Mapper
import org.springframework.stereotype.Component

@Component
class RoleMapper(
    val authorityMapper: AuthorityMapper
) : Mapper<Role, RoleDto> {

    override fun toDto(e: Role): RoleDto {
        return RoleDto(
            id = e.id,
            name = e.name,
            authorities = e.authorities.map { authorityMapper.toDto(it) }.toSet()
        )
    }

    override fun toEntity(d: RoleDto): Role {
        return Role(
            id = d.id,
            name = d.name,
            authorities = d.authorityIds!!.map { Authority(id = it) }.toMutableSet()
        )
    }
}