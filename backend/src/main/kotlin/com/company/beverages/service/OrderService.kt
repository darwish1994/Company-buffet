package com.company.beverages.service

import com.company.beverages.model.dto.request.CreateOrderRequest
import com.company.beverages.model.entity.Order
import com.company.beverages.model.entity.OrderItem
import com.company.beverages.model.enums.OrderStatus
import com.company.beverages.repository.BeverageRepository
import com.company.beverages.repository.OrderRepository
import com.company.beverages.repository.UserRepository
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

/**
 * Order Service
 * Handles order creation, updates, and tracking
 */
@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val beverageRepository: BeverageRepository,
    private val userRepository: UserRepository,
    private val messagingTemplate: SimpMessagingTemplate?
) {

    /**
     * Create new order
     */
    fun createOrder(request: CreateOrderRequest, userId: String): Order {
        logger.info { "Creating order for user: $userId" }

        val user = userRepository.findById(userId)
            .orElseThrow { throw IllegalArgumentException("User not found") }

        // Validate beverages and calculate total
        val orderItems = mutableListOf<OrderItem>()
        var totalPrice = 0.0

        request.items.forEach { item ->
            val beverage = beverageRepository.findById(item.beverageId)
                .orElseThrow { throw IllegalArgumentException("Beverage not found: ${item.beverageId}") }

            if (!beverage.available) {
                throw IllegalArgumentException("Beverage not available: ${beverage.name}")
            }

            val subtotal = beverage.price * item.quantity
            orderItems.add(
                OrderItem(
                    beverageId = beverage.id!!,
                    beverageName = beverage.name,
                    beverageNameAr = beverage.nameAr,
                    quantity = item.quantity,
                    price = beverage.price,
                    subtotal = subtotal
                )
            )
            totalPrice += subtotal
        }

        val order = Order(
            employeeId = user.id!!,
            employeeName = user.name,
            department = user.department,
            beverages = orderItems,
            totalPrice = totalPrice,
            notes = request.notes,
            status = OrderStatus.PENDING
        )

        val savedOrder = orderRepository.save(order)
        logger.info { "Order created successfully: ${savedOrder.id}" }

        // Broadcast to WebSocket (workers will be notified)
        broadcastOrderUpdate(savedOrder)

        return savedOrder
    }

    /**
     * Get orders with pagination
     */
    fun getOrders(pageable: Pageable): Page<Order> {
        return orderRepository.findAll(pageable)
    }

    /**
     * Get orders by employee
     */
    fun getOrdersByEmployee(employeeId: String, pageable: Pageable): Page<Order> {
        return orderRepository.findByEmployeeId(employeeId, pageable)
    }

    /**
     * Get orders by status
     */
    fun getOrdersByStatus(status: OrderStatus, pageable: Pageable): Page<Order> {
        return orderRepository.findByStatus(status, pageable)
    }

    /**
     * Get order by ID
     */
    fun getOrderById(orderId: String): Order {
        return orderRepository.findById(orderId)
            .orElseThrow { throw IllegalArgumentException("Order not found") }
    }

    /**
     * Update order status
     */
    fun updateOrderStatus(orderId: String, status: OrderStatus, workerId: String? = null): Order {
        val order = getOrderById(orderId)

        val worker = workerId?.let {
            userRepository.findById(it).orElse(null)
        }

        val updatedOrder = order.copy(
            status = status,
            workerId = worker?.id,
            workerName = worker?.name,
            completedDate = if (status == OrderStatus.DELIVERED) LocalDateTime.now() else order.completedDate
        )

        val saved = orderRepository.save(updatedOrder)
        logger.info { "Order ${orderId} status updated to $status by worker $workerId" }

        // Broadcast update
        broadcastOrderUpdate(saved)

        return saved
    }

    /**
     * Cancel order
     */
    fun cancelOrder(orderId: String, userId: String): Order {
        val order = getOrderById(orderId)

        if (order.employeeId != userId) {
            throw IllegalArgumentException("You can only cancel your own orders")
        }

        if (order.status != OrderStatus.PENDING) {
            throw IllegalArgumentException("Order cannot be cancelled (status: ${order.status})")
        }

        val cancelled = order.copy(status = OrderStatus.CANCELLED)
        val saved = orderRepository.save(cancelled)

        broadcastOrderUpdate(saved)
        return saved
    }

    /**
     * Get pending orders for workers
     */
    fun getPendingOrders(): List<Order> {
        return orderRepository.findPendingOrdersWithoutWorker(OrderStatus.PENDING)
    }

    /**
     * Get orders statistics
     */
    fun getOrdersStatistics(startDate: LocalDateTime, endDate: LocalDateTime): Map<String, Any> {
        val orders = orderRepository.findOrdersInDateRange(startDate, endDate)

        return mapOf(
            "totalOrders" to orders.size,
            "totalRevenue" to orders.sumOf { it.totalPrice },
            "pendingOrders" to orders.count { it.status == OrderStatus.PENDING },
            "completedOrders" to orders.count { it.status == OrderStatus.DELIVERED },
            "cancelledOrders" to orders.count { it.status == OrderStatus.CANCELLED }
        )
    }

    /**
     * Broadcast order update via WebSocket
     */
    private fun broadcastOrderUpdate(order: Order) {
        try {
            messagingTemplate?.convertAndSend("/topic/orders", order)
            // Send to specific employee
            messagingTemplate?.convertAndSendToUser(
                order.employeeId,
                "/queue/orders",
                order
            )
        } catch (e: Exception) {
            logger.error { "Failed to broadcast order update: ${e.message}" }
        }
    }
}
