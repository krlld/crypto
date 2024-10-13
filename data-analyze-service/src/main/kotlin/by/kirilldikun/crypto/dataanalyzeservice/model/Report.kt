package by.kirilldikun.crypto.dataanalyzeservice.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import org.springframework.data.annotation.CreatedDate

@Entity
@Table(name = "data_analyze_service_reports")
class Report(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val title: String = "",

    val sourceFileId: String = "",

    val resultFileId: String? = null,

    val userId: Long = 0,

    val isPublic: Boolean = false,

    @CreatedDate
    @Column(updatable = false)
    val createdAtDate: LocalDate = LocalDate.now()
)