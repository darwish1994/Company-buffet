package com.company.beverages.repository

import com.company.beverages.model.entity.Report
import com.company.beverages.model.enums.ReportType
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/**
 * Repository for Report entity
 */
@Repository
interface ReportRepository : MongoRepository<Report, String> {

    fun findByType(type: ReportType): List<Report>

    fun findByGeneratedBy(userId: String): List<Report>

    fun findByCreatedAtBetween(start: LocalDateTime, end: LocalDateTime): List<Report>

    fun findByTypeAndCreatedAtAfter(type: ReportType, createdAt: LocalDateTime): List<Report>
}
