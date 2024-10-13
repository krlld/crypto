package by.kirilldikun.crypto.dataanalyzeservice.service.impl

import by.kirilldikun.crypto.commons.util.TokenHelper
import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import by.kirilldikun.crypto.dataanalyzeservice.mapper.ReportMapper
import by.kirilldikun.crypto.dataanalyzeservice.producer.ReportCreationProducer
import by.kirilldikun.crypto.dataanalyzeservice.repository.ReportRepository
import by.kirilldikun.crypto.dataanalyzeservice.service.ReportService
import org.springframework.stereotype.Service

@Service
class ReportServiceImpl(
    val reportRepository: ReportRepository,
    val reportMapper: ReportMapper,
    val reportCreationProducer: ReportCreationProducer,
    val tokenHelper: TokenHelper
) : ReportService {

    override fun save(reportDto: ReportDto): ReportDto {
        return simpleSave(reportDto)
    }

    override fun generateReport(reportDto: ReportDto): ReportDto {
        val userId = tokenHelper.getUserId()
        val reportDtoWithUserId = reportDto.copy(userId = userId)
        val savedReport = simpleSave(reportDtoWithUserId)
        reportCreationProducer.send(savedReport)
        return savedReport
    }

    fun simpleSave(reportDto: ReportDto): ReportDto {
        val report = reportMapper.toEntity(reportDto)
        val savedReport = reportRepository.save(report)
        return reportMapper.toDto(savedReport)
    }
}