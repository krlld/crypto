package by.kirilldikun.crypto.authservice.service

import by.kirilldikun.crypto.commons.dto.RoleDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface RoleService {

    fun findAll(pageable: Pageable): Page<RoleDto>

    fun findById(id: Long): RoleDto

    fun save(roleDto: RoleDto): RoleDto

    fun update(id: Long, roleDto: RoleDto): RoleDto

    fun deleteById(id: Long)
}