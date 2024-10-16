package by.kirilldikun.crypto.externalapiservice.coincup.service

interface FavoriteCurrencyService {

    fun findAllUserCurrencyIds(userId: Long): List<String>

    fun changeFavoriteStatus(userId: Long, currencyId: String)
}