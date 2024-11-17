package by.kirilldikun.crypto.dataanalyzeservice.specification

import by.kirilldikun.crypto.dataanalyzeservice.dto.ReportFilterDto
import by.kirilldikun.crypto.dataanalyzeservice.model.Report
import jakarta.persistence.criteria.Predicate
import java.time.LocalDate
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class ReportSpecificationBuilder {

    fun build(filter: ReportFilterDto?): Specification<Report> {
        return Specification { root, queryObj, criteriaBuilder ->

            if (filter == null) {
                return@Specification criteriaBuilder.conjunction()
            }

            val predicates = mutableListOf<Predicate>()

            if (filter.userIds != null) {
                val userIdsPredicate = root.get<Long>("userId").`in`(filter.userIds)
                predicates.add(userIdsPredicate)
            }

            if (filter.createdAtDateStart != null && filter.createdAtDateEnd != null) {
                val createdAtPredicate = criteriaBuilder.between(
                    criteriaBuilder.function(
                        "DATE",
                        LocalDate::class.java,
                        root.get<LocalDate>("createdAtDate")
                    ),
                    filter.createdAtDateStart,
                    filter.createdAtDateEnd
                )
                predicates.add(createdAtPredicate)
            }

            if (filter.createdAtDateStart != null && filter.createdAtDateEnd == null) {
                val createdAtPredicate = criteriaBuilder.greaterThanOrEqualTo(
                    criteriaBuilder.function(
                        "DATE",
                        LocalDate::class.java,
                        root.get<LocalDate>("createdAtDate")
                    ),
                    filter.createdAtDateStart
                )
                predicates.add(createdAtPredicate)
            }

            if (filter.createdAtDateStart == null && filter.createdAtDateEnd != null) {
                val createdAtPredicate = criteriaBuilder.lessThanOrEqualTo(
                    criteriaBuilder.function(
                        "DATE",
                        LocalDate::class.java,
                        root.get<LocalDate>("createdAtDate")
                    ),
                    filter.createdAtDateEnd
                )
                predicates.add(createdAtPredicate)
            }

            if (!filter.search.isNullOrEmpty()) {
                val titlePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%${filter.search.lowercase()}%"
                )
                val descriptionPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("description")),
                    "%${filter.search.lowercase()}%"
                )
                val generalPredicate = criteriaBuilder.or(titlePredicate, descriptionPredicate)
                predicates.add(generalPredicate)
            }

            if (filter.isPublic != null) {
                val isPublicPredicate = criteriaBuilder.equal(
                    root.get<Boolean>("isPublic"),
                    filter.isPublic
                )
                predicates.add(isPublicPredicate)
            }

            return@Specification criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}