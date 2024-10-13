package by.kirilldikun.crypto.dataanalyzeservice.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ReportDto(

    val id: Long? = null,

    val title: String,

    val sourceFileId: String,

    val resultFileId: String? = null,

    val userId: Long? = null,

    val createdAtDate: LocalDate? = null
)
