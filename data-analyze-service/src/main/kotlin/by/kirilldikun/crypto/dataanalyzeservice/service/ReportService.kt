package by.kirilldikun.crypto.dataanalyzeservice.service

import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto

interface ReportService {

    fun generateReport(reportDto: ReportDto): ReportDto
}