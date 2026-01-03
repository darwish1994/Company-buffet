package com.company.beverages.model.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

/**
 * Notification entity for tracking sent notifications
 */
@Document(collection = "notifications")
data class Notification(
    @Id
    val id: String? = null,

    @Field("user_id")
    @Indexed
    val userId: String,

    @Field("title")
    val title: String,

    @Field("body")
    val body: String,

    @Field("type")
    val type: String, // ORDER_STATUS, SYSTEM, PROMOTION, etc.

    @Field("data")
    val data: Map<String, String> = emptyMap(),

    @Field("read")
    val read: Boolean = false,

    @Field("sent")
    val sent: Boolean = false,

    @CreatedDate
    @Field("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
