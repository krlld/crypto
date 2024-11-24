package by.kirilldikun.crypto.externalapiservice.alphavantage.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class NewsDto(

    val title: String,

    val url: String,

    @JsonProperty("time_published")
    val timePublished: String,

    val summary: String,

    @JsonProperty("banner_image")
    val bannerImage: String? = null,

    val source: String
)
