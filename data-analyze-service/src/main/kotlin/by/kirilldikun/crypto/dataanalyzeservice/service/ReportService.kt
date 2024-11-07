package by.kirilldikun.crypto.dataanalyzeservice.service

import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportFilterDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReportService {

    fun findAllPublic(reportFilterDto: ReportFilterDto? = null, pageable: Pageable): Page<ReportDto>

    fun findAllUserReports(reportFilterDto: ReportFilterDto? = null, pageable: Pageable): Page<ReportDto>

    fun findById(reportId: Long): ReportDto

    fun save(reportDto: ReportDto): ReportDto

    fun generateReport(reportDto: ReportDto): ReportDto

    fun changeFavoriteStatus(reportId: Long)
    fun isInFavoriteByIds(reportIds: List<Long>): Map<Long, Boolean>
}