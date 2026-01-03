package com.company.beverages.model.entity

import com.company.beverages.model.enums.ReportType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

/**
 * Report entity for storing generated reports
 *
 * Allows caching and historical tracking of reports
 */
@Document(collection = "reports")
data class Report(
    @Id
    val id: String? = null,

    @Field("type")
    @Indexed
    val type: ReportType,

    @Field("title")
    val title: String,

    @Field("generated_by")
    val generatedBy: String, // User ID

    @Field("start_date")
    val startDate: LocalDateTime,

    @Field("end_date")
    val endDate: LocalDateTime,

    @Field("data")
    val data: Map<String, Any>, // Flexible data structure for different report types

    @Field("filters")
    val filters: Map<String, String> = emptyMap(),

    @CreatedDate
    @Field("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
