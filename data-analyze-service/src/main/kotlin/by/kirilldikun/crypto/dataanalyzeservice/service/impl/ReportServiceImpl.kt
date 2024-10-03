package by.kirilldikun.crypto.dataanalyzeservice.service.impl

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
    val reportCreationProducer: ReportCreationProducer
) : ReportService {

    override fun generateReport(reportDto: ReportDto): ReportDto {
        val reportDtoWithUserId = reportDto.copy(userId = 0)
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