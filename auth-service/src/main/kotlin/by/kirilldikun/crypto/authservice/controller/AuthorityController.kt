package by.kirilldikun.crypto.authservice.controller

import by.kirilldikun.crypto.commons.dto.AuthorityDto
import by.kirilldikun.crypto.authservice.service.AuthorityService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/authorities")
class AuthorityController(
    val authorityService: AuthorityService
) {

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.commons.config.Authorities).MANAGE_ROLES)")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(pageable: Pageable): Page<AuthorityDto> {
        return authorityService.findAll(pageable)
    }

    @GetMapping("/my-authorities")
    @ResponseStatus(HttpStatus.OK)
    fun findAllMyAuthorities(): List<AuthorityDto> {
        return authorityService.findAllMyAuthorities()
    }
}