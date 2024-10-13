package by.kirilldikun.crypto.dataanalyzeservice.service

import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReportService {

    fun findAllPublic(pageable: Pageable): Page<ReportDto>

    fun findUserReports(pageable: Pageable): Page<ReportDto>

    fun save(reportDto: ReportDto): ReportDto

    fun generateReport(reportDto: ReportDto): ReportDto
}