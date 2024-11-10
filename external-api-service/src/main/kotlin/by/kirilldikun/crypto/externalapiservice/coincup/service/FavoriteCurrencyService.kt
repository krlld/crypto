package by.kirilldikun.crypto.externalapiservice.coincup.service

interface FavoriteCurrencyService {

    fun isInUserFavoriteByIds(userId: Long, currencyIds: List<String>): Map<String, Boolean>

    fun changeFavoriteStatus(userId: Long, currencyId: String)
}