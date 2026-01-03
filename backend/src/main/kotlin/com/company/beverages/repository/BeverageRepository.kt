package com.company.beverages.repository

import com.company.beverages.model.entity.Beverage
import com.company.beverages.model.enums.BeverageCategory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

/**
 * Repository for Beverage entity
 */
@Repository
interface BeverageRepository : MongoRepository<Beverage, String> {

    fun findByCategory(category: BeverageCategory, pageable: Pageable): Page<Beverage>

    fun findByAvailable(available: Boolean, pageable: Pageable): Page<Beverage>

    fun findByCategoryAndAvailable(
        category: BeverageCategory,
        available: Boolean,
        pageable: Pageable
    ): Page<Beverage>

    @Query("{ '\$or': [ { 'name': { '\$regex': ?0, '\$options': 'i' } }, { 'name_ar': { '\$regex': ?0, '\$options': 'i' } } ] }")
    fun searchByName(searchTerm: String, pageable: Pageable): Page<Beverage>

    fun findTop10ByOrderByTotalOrdersDesc(): List<Beverage>

    fun findTop10ByOrderByAverageRatingDesc(): List<Beverage>
}
