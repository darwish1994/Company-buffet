package com.company.beverages.model.dto.request

import com.company.beverages.model.enums.UserRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:Email(message = "Invalid email format")
    @field:NotBlank(message = "Email is required")
    val email: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters")
    val password: String,

    val role: UserRole = UserRole.EMPLOYEE,

    val department: String? = null,

    val employeeId: String? = null,

    val quota: Int = 10
)
