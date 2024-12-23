package by.kirilldikun.crypto.externalapiservice.coincup.service.impl

import by.kirilldikun.crypto.externalapiservice.coincup.dto.SubscriptionToPriceDto
import by.kirilldikun.crypto.externalapiservice.coincup.mapper.SubscribedPriceMapper
import by.kirilldikun.crypto.externalapiservice.coincup.repository.SubscriptionToPriceRepository
import by.kirilldikun.crypto.externalapiservice.coincup.service.SubscriptionToPriceService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubscriptionToPriceServiceImpl(
    val subscriptionToPriceRepository: SubscriptionToPriceRepository,
    val subscribedPriceMapper: SubscribedPriceMapper
) : SubscriptionToPriceService {

    @Transactional(readOnly = true)
    override fun findAllByUserId(pageable: Pageable, userId: Long): Page<SubscriptionToPriceDto> {
        return subscriptionToPriceRepository.findAllByUserId(pageable, userId)
            .map { subscribedPriceMapper.toDto(it) }
    }

    @Transactional
    override fun save(subscriptionToPriceDto: SubscriptionToPriceDto): SubscriptionToPriceDto {
        val subscriptionToPrice = subscribedPriceMapper.toEntity(subscriptionToPriceDto)
        val savedSubscriptionToPrice = subscriptionToPriceRepository.save(subscriptionToPrice)
        return subscribedPriceMapper.toDto(savedSubscriptionToPrice)
    }
}