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
}