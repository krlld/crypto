package by.kirilldikun.crypto.dataanalyzeservice.repository

import by.kirilldikun.crypto.dataanalyzeservice.model.FavoriteReport
import org.springframework.data.jpa.repository.JpaRepository

interface FavoriteReportRepository : JpaRepository<FavoriteReport, Long> {

    fun findAllByUserIdAndReportIdIn(userId: Long, ids: List<Long>): List<FavoriteReport>

    fun findByUserIdAndReportId(userId: Long, reportId: Long): FavoriteReport?
}