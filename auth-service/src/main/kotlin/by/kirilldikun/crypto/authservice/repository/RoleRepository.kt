package by.kirilldikun.crypto.authservice.repository

import by.kirilldikun.crypto.authservice.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long>