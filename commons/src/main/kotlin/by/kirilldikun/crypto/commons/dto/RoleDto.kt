package by.kirilldikun.crypto.commons.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RoleDto(

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long? = null,

    val name: String,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val authorityIds: Set<Long>? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val authorities: Set<AuthorityDto>? = null
)