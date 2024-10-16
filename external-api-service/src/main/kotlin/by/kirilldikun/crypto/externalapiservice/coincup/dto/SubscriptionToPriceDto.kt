package by.kirilldikun.crypto.externalapiservice.coincup.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class SubscriptionToPriceDto(

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val userId: Long,

    val currencyId: String,

    val price: BigDecimal
)
