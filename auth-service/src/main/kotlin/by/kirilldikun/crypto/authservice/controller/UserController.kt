package by.kirilldikun.crypto.authservice.controller

import by.kirilldikun.crypto.authservice.dto.ProfileDto
import by.kirilldikun.crypto.authservice.service.ProfileService
import by.kirilldikun.crypto.authservice.service.UserRoleService
import by.kirilldikun.crypto.authservice.service.UserService
import by.kirilldikun.crypto.commons.dto.UserDto
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findByEmail(@RequestParam email: String): UserDetails {
        return userDetailsService.loadUserByUsername(email)
    }

    @GetMapping("/users-by-ids")
    @ResponseStatus(HttpStatus.OK)
    fun findAllByIds(@RequestParam ids: List<Long>): List<UserDto> {
        return userService.findAllByIds(ids)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateProfile(@PathVariable id: Long, @RequestBody @Valid profileDto: ProfileDto): ProfileDto {
        return profileService.update(id, profileDto)
    }

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.commons.config.Authorities).MANAGE_ROLES)")
    @PatchMapping("/reassign-roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun resignRoles(@RequestParam userId: Long, @RequestParam newRoleIds: List<Long>) {
        userRoleService.reassignRoles(userId, newRoleIds)
    }
}