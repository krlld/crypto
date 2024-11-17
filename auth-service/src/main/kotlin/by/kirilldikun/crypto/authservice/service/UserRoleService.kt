package by.kirilldikun.crypto.authservice.service

import by.kirilldikun.crypto.authservice.dto.ReassignRolesDto

interface UserRoleService {

    fun reassignRoles(reassignRolesDto: ReassignRolesDto)
}