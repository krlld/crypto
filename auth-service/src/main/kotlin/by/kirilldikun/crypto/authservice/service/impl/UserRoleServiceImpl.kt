package by.kirilldikun.crypto.authservice.service.impl

import by.kirilldikun.crypto.authservice.dto.ReassignRolesDto
import by.kirilldikun.crypto.authservice.service.UserRoleService
import by.kirilldikun.crypto.authservice.service.UserService
import by.kirilldikun.crypto.commons.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRoleServiceImpl(
    val userService: UserService
) : UserRoleService {

    @Transactional
    override fun reassignRoles(reassignRolesDto: ReassignRolesDto) {
        val foundUser = userService.findAllByIds(listOf(reassignRolesDto.userId))
        if (foundUser.isEmpty()) {
            throw NotFoundException("User with id: ${reassignRolesDto.userId} not found")
        }
        val user = foundUser.first()
        val userWithNewRoles = user.copy(roleIds = reassignRolesDto.newRoleIds.toSet())
        userService.save(userWithNewRoles)
    }
}