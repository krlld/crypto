package by.kirilldikun.crypto.externalapiservice.coincup.controller

import by.kirilldikun.crypto.externalapiservice.coincup.service.CurrencyService
import by.kirilldikun.crypto.externalapiservice.coincup.dto.CurrencyData
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/currencies")
class CurrencyController(
    val currencyService: CurrencyService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<CurrencyData> {
        return currencyService.findAll()
    }

    @GetMapping("/favorites")
    @ResponseStatus(HttpStatus.OK)
    fun findAllFavorites(): List<CurrencyData> {
        return currencyService.findAllUserFavorites()
    }

    @PatchMapping("/favorites/{currencyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeFavoriteStatus(@PathVariable currencyId: String) {
        currencyService.changeFavoriteStatus(currencyId)
    }
}