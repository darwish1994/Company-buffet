package com.company.beverages.model.dto.request

import com.company.beverages.model.enums.BeverageCategory
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class CreateBeverageRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:NotBlank(message = "Arabic name is required")
    val nameAr: String,

    val description: String? = null,

    val descriptionAr: String? = null,

    val category: BeverageCategory,

    @field:Positive(message = "Price must be positive")
    val price: Double,

    val image: String? = null,

    val available: Boolean = true
)
