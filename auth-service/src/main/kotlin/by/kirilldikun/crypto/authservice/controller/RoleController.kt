package by.kirilldikun.crypto.authservice.controller

import by.kirilldikun.crypto.commons.dto.RoleDto
import by.kirilldikun.crypto.authservice.service.RoleService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/roles")
class RoleController(
    val roleService: RoleService
) {

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.authservice.config.Authorities).MANAGE_ROLES)")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(pageable: Pageable): Page<RoleDto> {
        return roleService.findAll(pageable)
    }

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.authservice.config.Authorities).MANAGE_ROLES)")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun findById(@PathVariable id: Long): RoleDto {
        return roleService.findById(id)
    }

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.authservice.config.Authorities).MANAGE_ROLES)")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody @Valid roleDto: RoleDto): RoleDto {
        return roleService.save(roleDto)
    }

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.authservice.config.Authorities).MANAGE_ROLES)")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable id: Long, @RequestBody @Valid roleDto: RoleDto): RoleDto {
        return roleService.update(id, roleDto)
    }

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.authservice.config.Authorities).MANAGE_ROLES)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        roleService.deleteById(id)
    }
}