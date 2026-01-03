package com.company.beverages

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing

/**
 * Main application class for the Beverages Order Management System
 *
 * This application provides a comprehensive platform for managing beverage orders
 * within a company, supporting multiple user roles (Admin, Employee, Worker, Management)
 * with real-time updates, reporting, and multi-language support.
 */
@SpringBootApplication
@EnableMongoAuditing
class BeveragesApplication

fun main(args: Array<String>) {
    runApplication<BeveragesApplication>(*args)
}
