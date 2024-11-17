package by.kirilldikun.crypto.authservice.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProfileDto(

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long? = null,

    val name: String,

    val lastname: String,

    val avatarId: String
)
