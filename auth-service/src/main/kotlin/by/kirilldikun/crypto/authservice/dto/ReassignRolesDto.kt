package by.kirilldikun.crypto.authservice.dto

data class ReassignRolesDto(

    val userId: Long,

    val newRoleIds: List<Long>
)