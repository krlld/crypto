package by.kirilldikun.crypto.externalapiservice.coincup.service

import by.kirilldikun.crypto.externalapiservice.coincup.dto.CurrencyData
import by.kirilldikun.crypto.externalapiservice.coincup.dto.SubscriptionToPriceDto

interface CurrencyService {

    fun findAll(): List<CurrencyData>

    fun isInUserFavoriteByIds(currencyIds: List<String>): Map<String, Boolean>

    fun changeFavoriteStatus(currencyId: String)

    fun findAllUserSubscriptionToPrices(): List<SubscriptionToPriceDto>

    fun subscribeToPrice(subscriptionToPriceDto: SubscriptionToPriceDto): SubscriptionToPriceDto
}