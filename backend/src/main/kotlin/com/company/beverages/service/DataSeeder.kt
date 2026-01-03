package com.company.beverages.service

import com.company.beverages.model.entity.Beverage
import com.company.beverages.model.entity.User
import com.company.beverages.model.enums.BeverageCategory
import com.company.beverages.model.enums.UserRole
import com.company.beverages.repository.BeverageRepository
import com.company.beverages.repository.UserRepository
import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * Data Seeder
 * Seeds the database with initial data for testing
 */
@Component
class DataSeeder(
    private val userRepository: UserRepository,
    private val beverageRepository: BeverageRepository,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (userRepository.count() == 0L) {
            logger.info { "Seeding database with initial data..." }
            seedUsers()
            seedBeverages()
            logger.info { "Database seeding completed!" }
        } else {
            logger.info { "Database already contains data, skipping seeding" }
        }
    }

    private fun seedUsers() {
        val users = listOf(
            User(
                name = "Admin User",
                email = "admin@company.com",
                password = passwordEncoder.encode("admin123"),
                role = UserRole.ADMIN,
                department = "IT",
                employeeId = "EMP001",
                quota = 100
            ),
            User(
                name = "John Employee",
                email = "employee@company.com",
                password = passwordEncoder.encode("employee123"),
                role = UserRole.EMPLOYEE,
                department = "Sales",
                employeeId = "EMP002",
                quota = 10
            ),
            User(
                name = "Worker Staff",
                email = "worker@company.com",
                password = passwordEncoder.encode("worker123"),
                role = UserRole.WORKER,
                department = "Kitchen",
                employeeId = "EMP003",
                quota = 5
            ),
            User(
                name = "Manager Boss",
                email = "manager@company.com",
                password = passwordEncoder.encode("manager123"),
                role = UserRole.MANAGEMENT,
                department = "Management",
                employeeId = "EMP004",
                quota = 20
            ),
            User(
                name = "Sarah Employee",
                email = "sarah@company.com",
                password = passwordEncoder.encode("sarah123"),
                role = UserRole.EMPLOYEE,
                department = "Marketing",
                employeeId = "EMP005",
                quota = 10
            )
        )

        userRepository.saveAll(users)
        logger.info { "Seeded ${users.size} users" }
    }

    private fun seedBeverages() {
        val beverages = listOf(
            // Hot Drinks
            Beverage(
                name = "Espresso",
                nameAr = "اسبريسو",
                description = "Strong black coffee",
                descriptionAr = "قهوة سوداء قوية",
                category = BeverageCategory.HOT_DRINKS,
                price = 3.50,
                available = true
            ),
            Beverage(
                name = "Cappuccino",
                nameAr = "كابتشينو",
                description = "Espresso with steamed milk and foam",
                descriptionAr = "اسبريسو مع حليب مبخر ورغوة",
                category = BeverageCategory.HOT_DRINKS,
                price = 4.50,
                available = true
            ),
            Beverage(
                name = "Latte",
                nameAr = "لاتيه",
                description = "Espresso with steamed milk",
                descriptionAr = "اسبريسو مع حليب مبخر",
                category = BeverageCategory.HOT_DRINKS,
                price = 4.00,
                available = true
            ),
            Beverage(
                name = "Green Tea",
                nameAr = "شاي أخضر",
                description = "Fresh green tea",
                descriptionAr = "شاي أخضر طازج",
                category = BeverageCategory.HOT_DRINKS,
                price = 2.50,
                available = true
            ),

            // Cold Drinks
            Beverage(
                name = "Iced Coffee",
                nameAr = "قهوة مثلجة",
                description = "Cold brewed coffee with ice",
                descriptionAr = "قهوة باردة مع ثلج",
                category = BeverageCategory.COLD_DRINKS,
                price = 4.00,
                available = true
            ),
            Beverage(
                name = "Iced Latte",
                nameAr = "لاتيه مثلج",
                description = "Cold latte with ice",
                descriptionAr = "لاتيه بارد مع ثلج",
                category = BeverageCategory.COLD_DRINKS,
                price = 4.50,
                available = true
            ),
            Beverage(
                name = "Iced Tea",
                nameAr = "شاي مثلج",
                description = "Refreshing iced tea",
                descriptionAr = "شاي مثلج منعش",
                category = BeverageCategory.COLD_DRINKS,
                price = 3.00,
                available = true
            ),

            // Juices
            Beverage(
                name = "Orange Juice",
                nameAr = "عصير برتقال",
                description = "Fresh squeezed orange juice",
                descriptionAr = "عصير برتقال طازج",
                category = BeverageCategory.JUICES,
                price = 5.00,
                available = true
            ),
            Beverage(
                name = "Apple Juice",
                nameAr = "عصير تفاح",
                description = "Pure apple juice",
                descriptionAr = "عصير تفاح نقي",
                category = BeverageCategory.JUICES,
                price = 4.50,
                available = true
            ),
            Beverage(
                name = "Mango Juice",
                nameAr = "عصير مانجو",
                description = "Tropical mango juice",
                descriptionAr = "عصير مانجو استوائي",
                category = BeverageCategory.JUICES,
                price = 5.50,
                available = true
            ),

            // Smoothies
            Beverage(
                name = "Berry Smoothie",
                nameAr = "سموذي التوت",
                description = "Mixed berries smoothie",
                descriptionAr = "سموذي التوت المشكل",
                category = BeverageCategory.SMOOTHIES,
                price = 6.50,
                available = true
            ),
            Beverage(
                name = "Banana Smoothie",
                nameAr = "سموذي الموز",
                description = "Creamy banana smoothie",
                descriptionAr = "سموذي الموز الكريمي",
                category = BeverageCategory.SMOOTHIES,
                price = 6.00,
                available = true
            ),

            // Specialty
            Beverage(
                name = "Mocha",
                nameAr = "موكا",
                description = "Chocolate flavored coffee",
                descriptionAr = "قهوة بنكهة الشوكولاتة",
                category = BeverageCategory.SPECIALTY,
                price = 5.50,
                available = true
            ),
            Beverage(
                name = "Caramel Macchiato",
                nameAr = "ماكياتو كراميل",
                description = "Espresso with caramel",
                descriptionAr = "اسبريسو مع الكراميل",
                category = BeverageCategory.SPECIALTY,
                price = 5.50,
                available = true
            )
        )

        beverageRepository.saveAll(beverages)
        logger.info { "Seeded ${beverages.size} beverages" }
    }
}
