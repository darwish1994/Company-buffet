package com.company.beverages.model.entity

import com.company.beverages.model.enums.UserRole
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

/**
 * User entity representing all system users
 *
 * Supports multiple roles: ADMIN, EMPLOYEE, WORKER, MANAGEMENT
 */
@Document(collection = "users")
data class User(
    @Id
    val id: String? = null,

    @Field("name")
    val name: String,

    @Field("email")
    @Indexed(unique = true)
    val email: String,

    @Field("password")
    val password: String, // BCrypt hashed

    @Field("role")
    @Indexed
    val role: UserRole,

    @Field("department")
    val department: String? = null,

    @Field("employee_id")
    @Indexed(unique = true, sparse = true)
    val employeeId: String? = null,

    @Field("quota")
    val quota: Int = 0, // Daily/monthly beverage quota

    @Field("fcm_token")
    val fcmToken: String? = null, // For push notifications

    @Field("active")
    val active: Boolean = true,

    @Field("avatar")
    val avatar: String? = null,

    @Field("phone")
    val phone: String? = null,

    @CreatedDate
    @Field("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Field("updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
