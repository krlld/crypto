package by.kirilldikun.crypto.externalapiservice.coincup.service

import by.kirilldikun.crypto.externalapiservice.coincup.dto.SubscriptionToPriceDto

interface SubscriptionToPriceService {

    fun findAllByUserId(userId: Long): List<SubscriptionToPriceDto>

    fun save(subscriptionToPriceDto: SubscriptionToPriceDto): SubscriptionToPriceDto
}