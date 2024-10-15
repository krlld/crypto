package by.kirilldikun.crypto.externalapiservice.coincup.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "external_api_service_favorite_currencies")
class FavoriteCurrency(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val userId: Long,

    val currencyId: String
)