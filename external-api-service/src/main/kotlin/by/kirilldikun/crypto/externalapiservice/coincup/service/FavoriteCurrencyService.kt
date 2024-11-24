package by.kirilldikun.crypto.externalapiservice.coincup.service

import by.kirilldikun.crypto.externalapiservice.coincup.model.FavoriteCurrency

interface FavoriteCurrencyService {

    fun isInUserFavoriteByIds(userId: Long, currencyIds: List<String>): Map<String, Boolean>

    fun findAllByUserId(userId: Long): List<FavoriteCurrency>

    fun changeFavoriteStatus(userId: Long, currencyId: String)
}