package by.kirilldikun.crypto.dataanalyzeservice.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "data_analyze_service_favorite_reports")
class FavoriteReport(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val userId: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "report_id")
    val report: Report = Report()
)