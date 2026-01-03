package com.company.beverages.model.enums

/**
 * User roles in the system
 *
 * ADMIN: Full system access, manages users and beverages
 * EMPLOYEE: Can browse and order beverages
 * WORKER: Beverage service staff who prepare and deliver orders
 * MANAGEMENT: Can view reports and analytics
 */
enum class UserRole {
    ADMIN,
    EMPLOYEE,
    WORKER,
    MANAGEMENT
}
