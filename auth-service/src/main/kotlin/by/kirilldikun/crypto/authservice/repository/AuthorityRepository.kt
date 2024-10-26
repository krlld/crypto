package by.kirilldikun.crypto.authservice.repository

import by.kirilldikun.crypto.authservice.model.Authority
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepository : JpaRepository<Authority, Long>