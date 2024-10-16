package by.kirilldikun.crypto.externalapiservice.coincup.service.impl

import by.kirilldikun.crypto.commons.util.TokenHelper
import by.kirilldikun.crypto.externalapiservice.coincup.dto.CurrencyData
import by.kirilldikun.crypto.externalapiservice.coincup.dto.SubscriptionToPriceDto
import by.kirilldikun.crypto.externalapiservice.coincup.feign.CoinCapFeignClient
import by.kirilldikun.crypto.externalapiservice.coincup.service.CurrencyService
import by.kirilldikun.crypto.externalapiservice.coincup.service.FavoriteCurrencyService
import by.kirilldikun.crypto.externalapiservice.coincup.service.SubscriptionToPriceService
import org.springframework.stereotype.Service

@Service
class CurrencyServiceImpl(
    val coinCapFeignClient: CoinCapFeignClient,
    val favoriteCurrencyService: FavoriteCurrencyService,
    val subscriptionToPriceService: SubscriptionToPriceService,
    val tokenHelper: TokenHelper
) : CurrencyService {

    override fun findAll(): List<CurrencyData> {
        return coinCapFeignClient.getCurrencies().data
    }

    override fun findAllUserFavorites(): List<CurrencyData> {
        val userId = tokenHelper.getUserId()
        val favoriteCurrencies = favoriteCurrencyService.findAllUserCurrencyIds(userId)
        return coinCapFeignClient.getCurrencies(favoriteCurrencies).data
    }

    override fun changeFavoriteStatus(currencyId: String) {
        val userId = tokenHelper.getUserId()
        favoriteCurrencyService.changeFavoriteStatus(userId, currencyId)
    }

    override fun findAllUserSubscriptionToPrices(): List<SubscriptionToPriceDto> {
        val userId = tokenHelper.getUserId()
        return subscriptionToPriceService.findAllByUserId(userId)
    }

    override fun subscribeToPrice(subscriptionToPriceDto: SubscriptionToPriceDto): SubscriptionToPriceDto {
        val userId = tokenHelper.getUserId()
        val subscriptionToPriceDtoWithUserId = subscriptionToPriceDto.copy(userId = userId)
        return subscriptionToPriceService.save(subscriptionToPriceDtoWithUserId)
    }
}