package com.company.beverages.model.dto.response

import com.company.beverages.model.enums.UserRole

data class AuthResponse(
    val token: String,
    val user: UserResponse
)

data class UserResponse(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val department: String?,
    val employeeId: String?,
    val quota: Int,
    val active: Boolean
)
