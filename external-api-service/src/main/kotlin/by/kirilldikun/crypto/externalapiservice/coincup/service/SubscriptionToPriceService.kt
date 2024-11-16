package by.kirilldikun.crypto.externalapiservice.coincup.service

import by.kirilldikun.crypto.externalapiservice.coincup.dto.SubscriptionToPriceDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SubscriptionToPriceService {

    fun findAllByUserId(pageable: Pageable, userId: Long): Page<SubscriptionToPriceDto>

    fun save(subscriptionToPriceDto: SubscriptionToPriceDto): SubscriptionToPriceDto
}