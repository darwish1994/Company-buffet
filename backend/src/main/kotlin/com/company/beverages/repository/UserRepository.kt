package com.company.beverages.repository

import com.company.beverages.model.entity.User
import com.company.beverages.model.enums.UserRole
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for User entity
 */
@Repository
interface UserRepository : MongoRepository<User, String> {

    fun findByEmail(email: String): Optional<User>

    fun findByEmployeeId(employeeId: String): Optional<User>

    fun findByRole(role: UserRole): List<User>

    fun findByDepartment(department: String): List<User>

    fun findByActive(active: Boolean): List<User>

    fun existsByEmail(email: String): Boolean

    fun existsByEmployeeId(employeeId: String): Boolean
}
