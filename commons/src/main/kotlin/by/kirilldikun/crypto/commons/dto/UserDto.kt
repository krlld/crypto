package by.kirilldikun.crypto.commons.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserDto(

    val id: Long,

    val email: String,

    val password: String,

    val name: String,

    val lastname: String,

    val avatarId: String,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val roleIds: Set<Long>? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val roles: Set<RoleDto>? = null
)
