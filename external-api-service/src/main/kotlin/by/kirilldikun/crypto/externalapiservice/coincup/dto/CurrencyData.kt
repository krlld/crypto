package by.kirilldikun.crypto.externalapiservice.coincup.dto

import java.math.BigDecimal

data class CurrencyData(

    val id: String,

    val rank: Long,

    val symbol: String,

    val name: String,

    val supply: BigDecimal,

    val maxSupply: BigDecimal? = null,

    val marketCapUsd: BigDecimal,

    val volumeUsd24Hr: BigDecimal,

    val priceUsd: BigDecimal,

    val changePercent24Hr: BigDecimal,

    val vwap24Hr: BigDecimal
)
