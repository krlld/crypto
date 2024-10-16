package by.kirilldikun.crypto.externalapiservice.coincup.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "external_api_service_subscription_to_prices")
class SubscriptionToPrice(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val userId: Long,

    val currencyId: String,

    val price: BigDecimal
)