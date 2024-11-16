package by.kirilldikun.crypto.externalapiservice.coincup.repository

import by.kirilldikun.crypto.externalapiservice.coincup.model.SubscriptionToPrice
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface SubscriptionToPriceRepository : JpaRepository<SubscriptionToPrice, Long> {

    fun findAllByUserId(pageable: Pageable, userId: Long): Page<SubscriptionToPrice>
}