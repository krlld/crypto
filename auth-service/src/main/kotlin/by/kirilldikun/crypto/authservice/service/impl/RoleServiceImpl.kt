package by.kirilldikun.crypto.authservice.service.impl

import by.kirilldikun.crypto.commons.dto.RoleDto
import by.kirilldikun.crypto.authservice.mapper.RoleMapper
import by.kirilldikun.crypto.authservice.repository.RoleRepository
import by.kirilldikun.crypto.authservice.service.RoleService
import by.kirilldikun.crypto.commons.exception.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoleServiceImpl(
    val roleRepository: RoleRepository,
    val roleMapper: RoleMapper
) : RoleService {

    @Transactional(readOnly = true)
    override fun findAll(pageable: Pageable): Page<RoleDto> {
        return roleRepository.findAll(pageable)
            .map { roleMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): RoleDto {
        return roleRepository.findById(id)
            .map { roleMapper.toDto(it) }
            .orElseThrow { NotFoundException("Role with id: $id not found") }
    }

    @Transactional
    override fun save(roleDto: RoleDto): RoleDto {
        val role = roleMapper.toEntity(roleDto)
        val savedRole = roleRepository.save(role)
        return roleMapper.toDto(savedRole)
    }

    @Transactional
    override fun update(id: Long, roleDto: RoleDto): RoleDto {
        findById(id)
        val roleWithId = roleDto.copy(id = id)
        return save(roleWithId)
    }

    @Transactional
    override fun deleteById(id: Long) {
        roleRepository.deleteById(id)
    }
}