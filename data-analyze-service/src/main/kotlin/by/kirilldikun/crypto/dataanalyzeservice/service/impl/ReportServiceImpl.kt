package by.kirilldikun.crypto.dataanalyzeservice.service.impl

import by.kirilldikun.crypto.commons.exception.BadRequestException
import by.kirilldikun.crypto.commons.exception.NotFoundException
import by.kirilldikun.crypto.commons.util.TokenHelper
import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportFilterDto
import by.kirilldikun.crypto.dataanalyzeservice.mapper.ReportMapper
import by.kirilldikun.crypto.dataanalyzeservice.producer.ReportCreationProducer
import by.kirilldikun.crypto.dataanalyzeservice.repository.ReportRepository
import by.kirilldikun.crypto.dataanalyzeservice.service.FavoriteReportService
import by.kirilldikun.crypto.dataanalyzeservice.service.ReportService
import by.kirilldikun.crypto.dataanalyzeservice.specification.ReportSpecificationBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportServiceImpl(
    val reportRepository: ReportRepository,
    val favoriteReportService: FavoriteReportService,
    val reportMapper: ReportMapper,
    val reportSpecificationBuilder: ReportSpecificationBuilder,
    val reportCreationProducer: ReportCreationProducer,
    val tokenHelper: TokenHelper
) : ReportService {

    @Transactional(readOnly = true)
    override fun findAllPublic(reportFilterDto: ReportFilterDto?, pageable: Pageable): Page<ReportDto> {
        val reportFilterDtoWithPublic = reportFilterDto?.copy(isPublic = true)
            ?: ReportFilterDto(isPublic = true)
        val specification = reportSpecificationBuilder.build(reportFilterDtoWithPublic)
        return reportRepository.findAll(specification, pageable)
            .map { reportMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    override fun findAllUserReports(reportFilterDto: ReportFilterDto?, pageable: Pageable): Page<ReportDto> {
        val userId = tokenHelper.getUserId()
        val reportFilterDtoWithPublic = reportFilterDto?.copy(userIds = listOf(userId), isPublic = null)
            ?: ReportFilterDto(userIds = listOf(userId))
        val specification = reportSpecificationBuilder.build(reportFilterDtoWithPublic)
        return reportRepository.findAll(specification, pageable)
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

    @Transactional(readOnly = true)
    override fun isInFavoriteByIds(reportIds: List<Long>): Map<Long, Boolean> {
        return favoriteReportService.isInFavoriteByIds(reportIds)
    }
}
