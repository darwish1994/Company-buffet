package com.company.beverages.model.dto.response

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)

data class PageResponse<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val last: Boolean
)

data class ErrorResponse(
    val error: String,
    val message: String,
    val timestamp: String = java.time.LocalDateTime.now().toString()
)
