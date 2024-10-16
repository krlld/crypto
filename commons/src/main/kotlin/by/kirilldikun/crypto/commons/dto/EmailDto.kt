package by.kirilldikun.crypto.commons.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class EmailDto(

    @JsonProperty("text")
    val text: String,

    @JsonProperty("subject")
    val subject: String,

    @JsonProperty("to")
    val to: List<String>,

    @JsonProperty("attachments")
    val attachments: List<Pair<String, String>> = emptyList()
)
