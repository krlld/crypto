package by.kirilldikun.crypto.authservice.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "auth_service_users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val email: String = "",

    val password: String = "",

    val name: String = "",

    val lastname: String = "",

    val avatarId: String = ""
)