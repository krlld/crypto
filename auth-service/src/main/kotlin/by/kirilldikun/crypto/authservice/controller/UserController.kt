package by.kirilldikun.crypto.authservice.controller

import by.kirilldikun.crypto.authservice.dto.ProfileDto
import by.kirilldikun.crypto.authservice.dto.ReassignRolesDto
import by.kirilldikun.crypto.authservice.service.ProfileService
import by.kirilldikun.crypto.authservice.service.UserRoleService
import by.kirilldikun.crypto.authservice.service.UserService
import by.kirilldikun.crypto.commons.dto.UserDto
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/users")
class UserController(
    val userDetailsService: UserDetailsService,
    val userService: UserService,
    val profileService: ProfileService,
    val userRoleService: UserRoleService
) {

    @GetMapping("/by-email")
    @ResponseStatus(HttpStatus.OK)
    fun findByEmail(@RequestParam email: String): UserDetails {
        return userDetailsService.loadUserByUsername(email)
    }

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.commons.config.Authorities).MANAGE_USERS)")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(pageable: Pageable): Page<UserDto> {
        return userService.findAll(pageable)
    }

    @GetMapping("/users-by-ids")
    @ResponseStatus(HttpStatus.OK)
    fun findAllByIds(@RequestParam ids: List<Long>): List<UserDto> {
        return userService.findAllByIds(ids)
    }

    @GetMapping("/about-me")
    @ResponseStatus(HttpStatus.OK)
    fun aboutMe(): UserDto {
        return userService.aboutMe()
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun updateProfile(@RequestBody @Valid profileDto: ProfileDto): ProfileDto {
        return profileService.update(profileDto)
    }

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.commons.config.Authorities).MANAGE_USERS)")
    @PatchMapping("/reassign-roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun resignRoles(@RequestBody reassignRolesDto: ReassignRolesDto) {
        userRoleService.reassignRoles(reassignRolesDto)
    }
}