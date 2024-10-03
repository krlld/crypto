package by.kirilldikun.crypto.dataanalyzeservice.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ReportDto(

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long? = null,

    val title: String,

    val sourceFileId: String,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val resultFileId: String? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val userId: Long? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val createdAtDate: LocalDate? = null
)
