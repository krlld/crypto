package by.kirilldikun.crypto.externalapiservice.coincup.service

import by.kirilldikun.crypto.externalapiservice.coincup.dto.CurrencyData
import by.kirilldikun.crypto.externalapiservice.coincup.dto.SubscriptionToPriceDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CurrencyService {

    fun findAll(search: String?, pageable: Pageable): Page<CurrencyData>

    fun isInUserFavoriteByIds(currencyIds: List<String>): Map<String, Boolean>

    fun changeFavoriteStatus(currencyId: String)

    fun findAllUserSubscriptionToPrices(): List<SubscriptionToPriceDto>

    fun subscribeToPrice(subscriptionToPriceDto: SubscriptionToPriceDto): SubscriptionToPriceDto
}