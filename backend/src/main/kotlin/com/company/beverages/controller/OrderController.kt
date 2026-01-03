package com.company.beverages.controller

import com.company.beverages.model.dto.request.CreateOrderRequest
import com.company.beverages.model.dto.response.ApiResponse
import com.company.beverages.model.dto.response.PageResponse
import com.company.beverages.model.entity.Order
import com.company.beverages.model.enums.OrderStatus
import com.company.beverages.service.OrderService
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

/**
 * Order Controller
 * Handles order management endpoints
 */
@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    fun createOrder(
        @Valid @RequestBody request: CreateOrderRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<Order>> {
        val userId = authentication.name
        logger.info { "Creating order for user: $userId" }

        val order = orderService.createOrder(request, userId)

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Order created successfully",
                data = order
            )
        )
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGEMENT', 'WORKER')")
    fun getOrders(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(required = false) status: OrderStatus?
    ): ResponseEntity<ApiResponse<PageResponse<Order>>> {
        val pageable = PageRequest.of(page, size, Sort.by("orderDate").descending())

        val ordersPage = if (status != null) {
            orderService.getOrdersByStatus(status, pageable)
        } else {
            orderService.getOrders(pageable)
        }

        val pageResponse = PageResponse(
            content = ordersPage.content,
            page = ordersPage.number,
            size = ordersPage.size,
            totalElements = ordersPage.totalElements,
            totalPages = ordersPage.totalPages,
            last = ordersPage.isLast
        )

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Orders retrieved successfully",
                data = pageResponse
            )
        )
    }

    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('EMPLOYEE')")
    fun getMyOrders(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<PageResponse<Order>>> {
        val userId = authentication.name
        val pageable = PageRequest.of(page, size, Sort.by("orderDate").descending())

        val ordersPage = orderService.getOrdersByEmployee(userId, pageable)

        val pageResponse = PageResponse(
            content = ordersPage.content,
            page = ordersPage.number,
            size = ordersPage.size,
            totalElements = ordersPage.totalElements,
            totalPages = ordersPage.totalPages,
            last = ordersPage.isLast
        )

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Your orders retrieved successfully",
                data = pageResponse
            )
        )
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: String): ResponseEntity<ApiResponse<Order>> {
        val order = orderService.getOrderById(id)

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Order retrieved successfully",
                data = order
            )
        )
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    fun updateOrderStatus(
        @PathVariable id: String,
        @RequestParam status: OrderStatus,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<Order>> {
        val workerId = authentication.name
        val order = orderService.updateOrderStatus(id, status, workerId)

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Order status updated successfully",
                data = order
            )
        )
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    fun cancelOrder(
        @PathVariable id: String,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<Order>> {
        val userId = authentication.name
        val order = orderService.cancelOrder(id, userId)

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Order cancelled successfully",
                data = order
            )
        )
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    fun getPendingOrders(): ResponseEntity<ApiResponse<List<Order>>> {
        val orders = orderService.getPendingOrders()

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Pending orders retrieved successfully",
                data = orders
            )
        )
    }
}
