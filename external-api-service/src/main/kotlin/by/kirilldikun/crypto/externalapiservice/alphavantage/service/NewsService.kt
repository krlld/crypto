package by.kirilldikun.crypto.externalapiservice.alphavantage.service

import by.kirilldikun.crypto.externalapiservice.alphavantage.dto.NewsDto

interface NewsService {

    fun findAll(): List<NewsDto>

    fun findAllAboutFavoriteCurrencies(): List<NewsDto>
}