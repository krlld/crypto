package by.kirilldikun.crypto.externalapiservice.coincup.service

import by.kirilldikun.crypto.externalapiservice.coincup.dto.CurrencyData

interface CurrencyService {

    fun findAll(): List<CurrencyData>

    fun findAllUserFavorites(): List<CurrencyData>

    fun changeFavoriteStatus(currencyId: String)
}