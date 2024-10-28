package by.kirilldikun.crypto.dataanalyzeservice.repository

import by.kirilldikun.crypto.dataanalyzeservice.model.Report
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ReportRepository : JpaRepository<Report, Long>, JpaSpecificationExecutor<Report>