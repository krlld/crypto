package by.kirilldikun.crypto.externalapiservice.coincup.controller

import by.kirilldikun.crypto.externalapiservice.coincup.service.CurrencyService
import by.kirilldikun.crypto.externalapiservice.coincup.dto.CurrencyData
import by.kirilldikun.crypto.externalapiservice.coincup.dto.SubscriptionToPriceDto
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/currencies")
class CurrencyController(
    val currencyService: CurrencyService
) {

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.commons.config.Authorities).MANAGE_CURRENCIES)")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(@RequestParam(required = false) search: String?, pageable: Pageable): Page<CurrencyData> {
        return currencyService.findAll(search, pageable)
    }

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.commons.config.Authorities).MANAGE_CURRENCIES)")
    @GetMapping("/is-in-favorite-by-ids")
    @ResponseStatus(HttpStatus.OK)
    fun isInFavoriteByIds(@RequestParam ids: List<String>): Map<String, Boolean> {
        return currencyService.isInUserFavoriteByIds(ids)
    }

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.commons.config.Authorities).MANAGE_CURRENCIES)")
    @PatchMapping("/favorites/{currencyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeFavoriteStatus(@PathVariable currencyId: String) {
        currencyService.changeFavoriteStatus(currencyId)
    }

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.commons.config.Authorities).SUBSCRIBE_TO_PRICE)")
    @GetMapping("/subscription-to-prices")
    @ResponseStatus(HttpStatus.OK)
    fun findAllUserSubscriptionToPrices(): List<SubscriptionToPriceDto> {
        return currencyService.findAllUserSubscriptionToPrices()
    }

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.commons.config.Authorities).SUBSCRIBE_TO_PRICE)")
    @PostMapping("/subscribe-to-price")
    @ResponseStatus(HttpStatus.CREATED)
    fun subscribeToPrice(@RequestBody @Valid subscriptionToPriceDto: SubscriptionToPriceDto) {
        currencyService.subscribeToPrice(subscriptionToPriceDto)
    }
}