package by.kirilldikun.crypto.dataanalyzeservice.service.impl

import by.kirilldikun.crypto.commons.util.TokenHelper
import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import by.kirilldikun.crypto.dataanalyzeservice.mapper.ReportMapper
import by.kirilldikun.crypto.dataanalyzeservice.producer.ReportCreationProducer
import by.kirilldikun.crypto.dataanalyzeservice.repository.ReportRepository
import by.kirilldikun.crypto.dataanalyzeservice.service.ReportService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportServiceImpl(
    val reportRepository: ReportRepository,
    val reportMapper: ReportMapper,
    val reportCreationProducer: ReportCreationProducer,
    val tokenHelper: TokenHelper
) : ReportService {

    @Transactional(readOnly = true)
    override fun findAll(pageable: Pageable): Page<ReportDto> {
        return reportRepository.findAllPublic(pageable)
            .map { reportMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    override fun findUserReports(pageable: Pageable): Page<ReportDto> {
        val userId = tokenHelper.getUserId()
        return reportRepository.findAllByUserId(userId, pageable)
            .map { reportMapper.toDto(it) }
    }

    @Transactional
    override fun save(reportDto: ReportDto): ReportDto {
        return simpleSave(reportDto)
    }

    @Transactional
    override fun generateReport(reportDto: ReportDto): ReportDto {
        val userId = tokenHelper.getUserId()
        val reportDtoWithUserId = reportDto.copy(userId = userId)
        val savedReport = simpleSave(reportDtoWithUserId)
        reportCreationProducer.send(savedReport)
        return savedReport
    }

    @Transactional
    fun simpleSave(reportDto: ReportDto): ReportDto {
        val report = reportMapper.toEntity(reportDto)
        val savedReport = reportRepository.save(report)
        return reportMapper.toDto(savedReport)
    }
}