package by.kirilldikun.crypto.dataanalyzeservice.dto

import java.time.LocalDate

data class ReportFilterDto(

    val userIds: List<Long>? = null,

    val createdAtDateStart: LocalDate? = null,

    val createdAtDateEnd: LocalDate? = null,

    val search: String? = null,

    val isPublic: Boolean? = null
)
