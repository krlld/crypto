package by.kirilldikun.crypto.dataanalyzeservice.mapper

import by.kirilldikun.crypto.commons.mapper.Mapper
import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportDto
import by.kirilldikun.crypto.dataanalyzeservice.model.Report
import org.springframework.stereotype.Component

@Component
class ReportMapper : Mapper<Report, ReportDto> {

    override fun toDto(e: Report): ReportDto {
        return ReportDto(
            id = e.id,
            title = e.title,
            sourceFileId = e.sourceFileId,
            resultFileId = e.resultFileId,
            userId = e.userId,
            isPublic = e.isPublic,
            createdAtDate = e.createdAtDate
        )
    }

    override fun toEntity(d: ReportDto): Report {
        return Report(
            id = d.id,
            title = d.title,
            sourceFileId = d.sourceFileId,
            resultFileId = d.resultFileId,
            userId = d.userId!!,
            isPublic = d.isPublic
        )
    }
}