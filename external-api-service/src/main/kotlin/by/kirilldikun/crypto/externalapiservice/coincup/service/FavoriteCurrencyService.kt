package by.kirilldikun.crypto.externalapiservice.coincup.service

interface FavoriteCurrencyService {

    fun findAllUserCurrencyIds(): List<String>

    fun changeFavoriteStatus(currencyId: String)
}