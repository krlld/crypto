package by.kirilldikun.crypto.dataanalyzeservice.controller

import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import by.kirilldikun.crypto.dataanalyzeservice.service.ReportService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/reports")
class ReportController(
    val reportService: ReportService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAllPublic(pageable: Pageable): Page<ReportDto> {
        return reportService.findAllPublic(pageable)
    }

    @GetMapping("/my-reports")
    @ResponseStatus(HttpStatus.OK)
    fun findUserReports(pageable: Pageable): Page<ReportDto> {
        return reportService.findUserReports(pageable)
    }

    @PostMapping("/generate-report")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun generateReport(@RequestBody @Valid reportDto: ReportDto): ReportDto {
        return reportService.generateReport(reportDto)
    }
}