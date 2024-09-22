package by.kirilldikun.crypto.authservice.repository

import by.kirilldikun.crypto.authservice.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): User?
}