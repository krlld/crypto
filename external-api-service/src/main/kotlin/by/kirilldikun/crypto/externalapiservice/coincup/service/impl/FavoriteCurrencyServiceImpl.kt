package by.kirilldikun.crypto.externalapiservice.coincup.service.impl

import by.kirilldikun.crypto.commons.util.TokenHelper
import by.kirilldikun.crypto.externalapiservice.coincup.model.FavoriteCurrency
import by.kirilldikun.crypto.externalapiservice.coincup.repository.FavoriteCurrencyRepository
import by.kirilldikun.crypto.externalapiservice.coincup.service.FavoriteCurrencyService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavoriteCurrencyServiceImpl(
    val favoriteCurrencyRepository: FavoriteCurrencyRepository,
    val tokenHelper: TokenHelper
) : FavoriteCurrencyService {

    @Transactional(readOnly = true)
    override fun findAllUserCurrencyIds(): List<String> {
        val userId = tokenHelper.getUserId()
        return favoriteCurrencyRepository.findAllByUserId(userId)
            .map { it.currencyId }
    }

    @Transactional
    override fun changeFavoriteStatus(currencyId: String) {
        val userId = tokenHelper.getUserId()
        val foundFavoriteCurrency = favoriteCurrencyRepository.findByUserIdAndCurrencyId(userId, currencyId)
        if (foundFavoriteCurrency == null) {
            favoriteCurrencyRepository.save(FavoriteCurrency(userId = userId, currencyId = currencyId))
            return
        }
        favoriteCurrencyRepository.delete(foundFavoriteCurrency)
    }
}