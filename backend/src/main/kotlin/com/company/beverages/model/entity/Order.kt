package com.company.beverages.model.entity

import com.company.beverages.model.enums.OrderStatus
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

/**
 * Order entity representing beverage orders
 *
 * Tracks the complete order lifecycle from placement to delivery
 */
@Document(collection = "orders")
data class Order(
    @Id
    val id: String? = null,

    @Field("employee_id")
    @Indexed
    val employeeId: String,

    @Field("employee_name")
    val employeeName: String,

    @Field("department")
    val department: String? = null,

    @Field("beverages")
    val beverages: List<OrderItem>,

    @Field("total_price")
    val totalPrice: Double,

    @Field("status")
    @Indexed
    val status: OrderStatus = OrderStatus.PENDING,

    @Field("notes")
    val notes: String? = null,

    @Field("order_date")
    @Indexed
    val orderDate: LocalDateTime = LocalDateTime.now(),

    @Field("completed_date")
    val completedDate: LocalDateTime? = null,

    @Field("rating")
    val rating: Int? = null, // Overall order rating

    @Field("rating_comment")
    val ratingComment: String? = null,

    @Field("worker_id")
    val workerId: String? = null,

    @Field("worker_name")
    val workerName: String? = null,

    @CreatedDate
    @Field("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Field("updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

/**
 * Order item subdocument
 */
data class OrderItem(
    @Field("beverage_id")
    val beverageId: String,

    @Field("beverage_name")
    val beverageName: String,

    @Field("beverage_name_ar")
    val beverageNameAr: String,

    @Field("quantity")
    val quantity: Int,

    @Field("price")
    val price: Double,

    @Field("subtotal")
    val subtotal: Double
)
