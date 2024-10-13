package by.kirilldikun.crypto.dataanalyzeservice.service.impl

import by.kirilldikun.crypto.commons.exception.BadRequestException
import by.kirilldikun.crypto.commons.exception.NotFoundException
import by.kirilldikun.crypto.commons.util.TokenHelper
import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import by.kirilldikun.crypto.dataanalyzeservice.mapper.ReportMapper
import by.kirilldikun.crypto.dataanalyzeservice.producer.ReportCreationProducer
import by.kirilldikun.crypto.dataanalyzeservice.repository.ReportRepository
import by.kirilldikun.crypto.dataanalyzeservice.service.FavoriteReportService
import by.kirilldikun.crypto.dataanalyzeservice.service.ReportService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportServiceImpl(
    val reportRepository: ReportRepository,
    val favoriteReportService: FavoriteReportService,
    val reportMapper: ReportMapper,
    val reportCreationProducer: ReportCreationProducer,
    val tokenHelper: TokenHelper
) : ReportService {

    @Transactional(readOnly = true)
    override fun findAllPublic(pageable: Pageable): Page<ReportDto> {
        return reportRepository.findAllPublic(pageable)
            .map { reportMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    override fun findUserReports(pageable: Pageable): Page<ReportDto> {
        val userId = tokenHelper.getUserId()
        return reportRepository.findAllByUserId(userId, pageable)
            .map { reportMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    override fun findById(reportId: Long): ReportDto {
        return reportRepository.findById(reportId)
            .map { reportMapper.toDto(it) }
            .orElseThrow { NotFoundException("Report with id $reportId not found") }
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

    @Transactional
    override fun changeFavoriteStatus(reportId: Long) {
        val userId = tokenHelper.getUserId()
        val report = findById(reportId)
        if (!report.isPublic && report.userId != userId) {
            throw BadRequestException("Report with id $reportId is not public")
        }
        favoriteReportService.changeFavoriteStatus(reportId)
    }
}
