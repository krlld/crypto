package by.kirilldikun.crypto.externalapiservice.coincup.mapper

import by.kirilldikun.crypto.commons.mapper.Mapper
import by.kirilldikun.crypto.externalapiservice.coincup.dto.SubscriptionToPriceDto
import by.kirilldikun.crypto.externalapiservice.coincup.model.SubscriptionToPrice
import org.springframework.stereotype.Component

@Component
class SubscribedPriceMapper : Mapper<SubscriptionToPrice, SubscriptionToPriceDto> {

    override fun toDto(e: SubscriptionToPrice): SubscriptionToPriceDto {
        return SubscriptionToPriceDto(
            id = e.id,
            userId = e.userId,
            currencyId = e.currencyId,
            comparisonType = e.comparisonType,
            price = e.price
        )
    }

    override fun toEntity(d: SubscriptionToPriceDto): SubscriptionToPrice {
        return SubscriptionToPrice(
            id = d.id,
            userId = d.userId,
            currencyId = d.currencyId,
            comparisonType = d.comparisonType,
            price = d.price
        )
    }
}