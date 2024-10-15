package by.kirilldikun.crypto.externalapiservice.coincup.service.impl

import by.kirilldikun.crypto.externalapiservice.coincup.dto.CurrencyData
import by.kirilldikun.crypto.externalapiservice.coincup.feign.CoinCapFeignClient
import by.kirilldikun.crypto.externalapiservice.coincup.service.CurrencyService
import by.kirilldikun.crypto.externalapiservice.coincup.service.FavoriteCurrencyService
import org.springframework.stereotype.Service

@Service
class CurrencyServiceImpl(
    val coinCapFeignClient: CoinCapFeignClient,
    val favoriteCurrencyService: FavoriteCurrencyService
) : CurrencyService {

    override fun findAll(): List<CurrencyData> {
        return coinCapFeignClient.getCurrencies().data
    }

    override fun findAllUserFavorites(): List<CurrencyData> {
        val favoriteCurrencies = favoriteCurrencyService.findAllUserCurrencyIds()
        return coinCapFeignClient.getCurrencies(favoriteCurrencies).data
    }

    override fun changeFavoriteStatus(currencyId: String) {
        favoriteCurrencyService.changeFavoriteStatus(currencyId)
    }
}