package by.kirilldikun.crypto.dataanalyzeservice.service

import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto

interface ReportService {

    fun save(reportDto: ReportDto): ReportDto

    fun generateReport(reportDto: ReportDto): ReportDto
}