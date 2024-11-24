package by.kirilldikun.crypto.externalapiservice.coincup.service

import by.kirilldikun.crypto.externalapiservice.coincup.dto.CurrencyDto
import by.kirilldikun.crypto.externalapiservice.coincup.dto.SubscriptionToPriceDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CurrencyService {

    fun findAll(search: String? = null, ids: List<String>? = null): List<CurrencyDto>

    fun findAllFavoriteCurrencies(): List<CurrencyDto>

    fun isInUserFavoriteByIds(currencyIds: List<String>): Map<String, Boolean>

    fun changeFavoriteStatus(currencyId: String)

    fun findAllUserSubscriptionToPrices(pageable: Pageable): Page<SubscriptionToPriceDto>

    fun subscribeToPrice(subscriptionToPriceDto: SubscriptionToPriceDto): SubscriptionToPriceDto
}