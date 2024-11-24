package by.kirilldikun.crypto.externalapiservice.alphavantage.controller

import by.kirilldikun.crypto.externalapiservice.alphavantage.dto.NewsDto
import by.kirilldikun.crypto.externalapiservice.alphavantage.service.NewsService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/news")
class NewsController(
    val newsService: NewsService
) {

    @PreAuthorize("hasAuthority(T(by.kirilldikun.crypto.commons.config.Authorities).MANAGE_NEWS)")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<NewsDto> {
        return newsService.findAll()
    }

    @PreAuthorize(
        """
        hasAuthority(T(by.kirilldikun.crypto.commons.config.Authorities).MANAGE_NEWS) &&
        hasAuthority(T(by.kirilldikun.crypto.commons.config.Authorities).MANAGE_CURRENCIES)
        """
    )
    @GetMapping("/about-favorite-currencies")
    @ResponseStatus(HttpStatus.OK)
    fun findAllAboutFavoriteCurrencies(): List<NewsDto> {
        return newsService.findAllAboutFavoriteCurrencies()
    }
}