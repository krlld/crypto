package by.kirilldikun.crypto.dataanalyzeservice.service

interface FavoriteReportService {

    fun changeFavoriteStatus(reportId: Long)

    fun isInFavoriteByIds(reportIds: List<Long>): Map<Long, Boolean>
}