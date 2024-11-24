package by.kirilldikun.crypto.dataanalyzeservice.service.impl

import by.kirilldikun.crypto.commons.util.TokenHelper
import by.kirilldikun.crypto.dataanalyzeservice.model.FavoriteReport
import by.kirilldikun.crypto.dataanalyzeservice.model.Report
import by.kirilldikun.crypto.dataanalyzeservice.repository.FavoriteReportRepository
import by.kirilldikun.crypto.dataanalyzeservice.service.FavoriteReportService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavoriteReportServiceImpl(
    val favoriteReportRepository: FavoriteReportRepository,
    val tokenHelper: TokenHelper
) : FavoriteReportService {

    @Transactional(readOnly = true)
    override fun findAllFavoriteReportIds(userId: Long): List<Long> {
        return favoriteReportRepository.findAllByUserId(userId)
            .map { it.report.id!! }
    }

    @Transactional
    override fun changeFavoriteStatus(reportId: Long) {
        val userId = tokenHelper.getUserId()
        val favoriteReport = favoriteReportRepository.findByUserIdAndReportId(userId, reportId)
        if (favoriteReport != null) {
            favoriteReportRepository.delete(favoriteReport)
        } else {
            favoriteReportRepository.save(
                FavoriteReport(
                    userId = userId,
                    report = Report(id = reportId)
                )
            )
        }
    }

    @Transactional(readOnly = true)
    override fun isInFavoriteByIds(reportIds: List<Long>): Map<Long, Boolean> {
        val userId = tokenHelper.getUserId()
        val favoriteReports = favoriteReportRepository.findAllByUserIdAndReportIdIn(userId, reportIds)
        val favoriteReportIds = favoriteReports.map { it.report.id }.toSet()
        return reportIds.associateWith { favoriteReportIds.contains(it) }
    }
}