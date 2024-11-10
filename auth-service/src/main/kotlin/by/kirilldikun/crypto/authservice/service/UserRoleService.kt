package by.kirilldikun.crypto.authservice.service

interface UserRoleService {

    fun reassignRoles(userId: Long, newRoleIds: List<Long>)
}