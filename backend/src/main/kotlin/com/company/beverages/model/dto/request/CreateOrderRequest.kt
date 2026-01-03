package com.company.beverages.model.dto.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive

data class CreateOrderRequest(
    @field:NotEmpty(message = "Order must contain at least one item")
    val items: List<OrderItemRequest>,

    val notes: String? = null
)

data class OrderItemRequest(
    val beverageId: String,

    @field:Positive(message = "Quantity must be positive")
    val quantity: Int
)
