package com.company.beverages.repository

import com.company.beverages.model.entity.Order
import com.company.beverages.model.enums.OrderStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/**
 * Repository for Order entity
 */
@Repository
interface OrderRepository : MongoRepository<Order, String> {

    fun findByEmployeeId(employeeId: String, pageable: Pageable): Page<Order>

    fun findByStatus(status: OrderStatus, pageable: Pageable): Page<Order>

    fun findByWorkerId(workerId: String?, pageable: Pageable): Page<Order>

    fun findByOrderDateBetween(start: LocalDateTime, end: LocalDateTime): List<Order>

    fun findByStatusAndWorkerId(status: OrderStatus, workerId: String?): List<Order>

    @Query("{ 'status': ?0, 'worker_id': null }")
    fun findPendingOrdersWithoutWorker(status: OrderStatus): List<Order>

    fun countByStatus(status: OrderStatus): Long

    fun countByEmployeeIdAndOrderDateBetween(
        employeeId: String,
        start: LocalDateTime,
        end: LocalDateTime
    ): Long

    @Query("{ 'order_date': { '\$gte': ?0, '\$lte': ?1 } }")
    fun findOrdersInDateRange(start: LocalDateTime, end: LocalDateTime): List<Order>

    fun findByDepartment(department: String, pageable: Pageable): Page<Order>
}
