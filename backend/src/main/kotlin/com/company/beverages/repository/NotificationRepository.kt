package com.company.beverages.repository

import com.company.beverages.model.entity.Notification
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Repository for Notification entity
 */
@Repository
interface NotificationRepository : MongoRepository<Notification, String> {

    fun findByUserId(userId: String): List<Notification>

    fun findByUserIdAndRead(userId: String, read: Boolean): List<Notification>

    fun countByUserIdAndRead(userId: String, read: Boolean): Long
}
