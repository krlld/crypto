package by.kirilldikun.crypto.dataanalyzeservice.repository

import by.kirilldikun.crypto.dataanalyzeservice.model.Report
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ReportRepository : JpaRepository<Report, Long> {

    @Query("FROM Report r WHERE r.isPublic = TRUE")
    fun findAllPublic(pageable: Pageable): Page<Report>

    fun findAllByUserId(userId: Long, pageable: Pageable): Page<Report>
}