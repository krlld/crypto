package by.kirilldikun.crypto.authservice.service.impl

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
    override fun reassignRoles(userId: Long, newRoleIds: List<Long>) {
        val foundUser = userService.findAllByIds(listOf(userId))
        if (foundUser.isEmpty()) {
            throw NotFoundException("User with id: $userId not found")
        }
        val user = foundUser.first()
        val userWithNewRoles = user.copy(roleIds = newRoleIds.toSet())
        userService.save(userWithNewRoles)
    }
}