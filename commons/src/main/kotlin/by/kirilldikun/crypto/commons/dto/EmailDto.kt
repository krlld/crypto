package by.kirilldikun.crypto.commons.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class EmailDto(

    @JsonProperty("text")
    val text: String,

    @JsonProperty("subject")
    val subject: String,

    @JsonProperty("toId")
    val toId: Long,

    @JsonProperty("attachments")
    val attachments: List<Pair<String, String>> = emptyList()
)
