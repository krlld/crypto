package by.kirilldikun.crypto.externalapiservice.coincup.dto

import java.math.BigDecimal

data class CurrencyDto(

    val id: String,

    val rank: Long,

    val symbol: String,

    val name: String,

    val supply: BigDecimal? = null,

    val maxSupply: BigDecimal? = null,

    val marketCapUsd: BigDecimal? = null,

    val volumeUsd24Hr: BigDecimal? = null,

    val priceUsd: BigDecimal? = null,

    val changePercent24Hr: BigDecimal? = null,

    val vwap24Hr: BigDecimal? = null
)
