package by.kirilldikun.crypto.externalapiservice.coincup.service.impl

import by.kirilldikun.crypto.externalapiservice.coincup.model.FavoriteCurrency
import by.kirilldikun.crypto.externalapiservice.coincup.repository.FavoriteCurrencyRepository
import by.kirilldikun.crypto.externalapiservice.coincup.service.FavoriteCurrencyService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavoriteCurrencyServiceImpl(
    val favoriteCurrencyRepository: FavoriteCurrencyRepository
) : FavoriteCurrencyService {

    @Transactional(readOnly = true)
    override fun isInUserFavoriteByIds(userId: Long, currencyIds: List<String>): Map<String, Boolean> {
        val favoriteCurrencies = favoriteCurrencyRepository.findAllByUserIdAndCurrencyIdIn(userId, currencyIds)
        val favoriteCurrencyIds = favoriteCurrencies.map { it.currencyId }
        return currencyIds.associateWith { favoriteCurrencyIds.contains(it) }
    }

    @Transactional
    override fun changeFavoriteStatus(userId: Long, currencyId: String) {
        val foundFavoriteCurrency = favoriteCurrencyRepository.findByUserIdAndCurrencyId(userId, currencyId)
        if (foundFavoriteCurrency == null) {
            favoriteCurrencyRepository.save(FavoriteCurrency(userId = userId, currencyId = currencyId))
            return
        }
        favoriteCurrencyRepository.delete(foundFavoriteCurrency)
    }
}