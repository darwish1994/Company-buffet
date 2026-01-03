package com.company.beverages.model.entity

import com.company.beverages.model.enums.BeverageCategory
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

/**
 * Beverage entity representing menu items
 *
 * Supports multi-language (English and Arabic) for name and description
 */
@Document(collection = "beverages")
data class Beverage(
    @Id
    val id: String? = null,

    @Field("name")
    val name: String,

    @Field("name_ar")
    val nameAr: String,

    @Field("description")
    val description: String? = null,

    @Field("description_ar")
    val descriptionAr: String? = null,

    @Field("category")
    @Indexed
    val category: BeverageCategory,

    @Field("price")
    val price: Double,

    @Field("image")
    val image: String? = null, // Image filename

    @Field("available")
    @Indexed
    val available: Boolean = true,

    @Field("ratings")
    val ratings: List<Rating> = emptyList(),

    @Field("average_rating")
    val averageRating: Double = 0.0,

    @Field("total_orders")
    val totalOrders: Int = 0,

    @CreatedDate
    @Field("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Field("updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

/**
 * Rating subdocument for beverages
 */
data class Rating(
    @Field("user_id")
    val userId: String,

    @Field("rating")
    val rating: Int, // 1-5 stars

    @Field("comment")
    val comment: String? = null,

    @Field("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
