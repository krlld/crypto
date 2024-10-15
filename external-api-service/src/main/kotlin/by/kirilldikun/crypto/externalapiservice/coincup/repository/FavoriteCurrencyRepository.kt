package by.kirilldikun.crypto.externalapiservice.coincup.repository

import by.kirilldikun.crypto.externalapiservice.coincup.model.FavoriteCurrency
import org.springframework.data.jpa.repository.JpaRepository

interface FavoriteCurrencyRepository : JpaRepository<FavoriteCurrency, Long> {

    fun findAllByUserId(userId: Long): List<FavoriteCurrency>

    fun findByUserIdAndCurrencyId(userId: Long, currencyId: String): FavoriteCurrency?
}