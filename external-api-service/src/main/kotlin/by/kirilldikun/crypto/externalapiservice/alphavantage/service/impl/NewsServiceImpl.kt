package by.kirilldikun.crypto.externalapiservice.alphavantage.service.impl

import by.kirilldikun.crypto.externalapiservice.alphavantage.dto.NewsDto
import by.kirilldikun.crypto.externalapiservice.alphavantage.feign.AlphavantageFeignClient
import by.kirilldikun.crypto.externalapiservice.alphavantage.service.NewsService
import by.kirilldikun.crypto.externalapiservice.coincup.service.CurrencyService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class NewsServiceImpl(
    @Value("\${alphavantage.api.key}")
    val apikey: String,
    val alphavantageFeignClient: AlphavantageFeignClient,
    val currencyService: CurrencyService
) : NewsService {

    override fun findAll(): List<NewsDto> {
        return alphavantageFeignClient.findAllNews(apikey = apikey).feed
    }

    override fun findAllAboutFavoriteCurrencies(): List<NewsDto> {
        val favoriteCurrencies = currencyService.findAllFavoriteCurrencies()
        val tickers = favoriteCurrencies.map { "CRYPTO:${it.symbol}" }
        return alphavantageFeignClient.findAllNews(tickers = tickers, apikey = apikey).feed
    }
}