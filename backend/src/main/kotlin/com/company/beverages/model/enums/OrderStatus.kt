package com.company.beverages.model.enums

/**
 * Order status workflow
 *
 * PENDING: Order placed, waiting to be picked up by worker
 * PREPARING: Worker is preparing the order
 * READY: Order is ready for pickup/delivery
 * DELIVERED: Order has been delivered to employee
 * CANCELLED: Order was cancelled
 */
enum class OrderStatus {
    PENDING,
    PREPARING,
    READY,
    DELIVERED,
    CANCELLED
}
