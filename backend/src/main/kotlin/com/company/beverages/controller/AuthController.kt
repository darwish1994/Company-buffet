package com.company.beverages.controller

import com.company.beverages.model.dto.request.LoginRequest
import com.company.beverages.model.dto.request.RegisterRequest
import com.company.beverages.model.dto.response.ApiResponse
import com.company.beverages.model.dto.response.AuthResponse
import com.company.beverages.service.AuthService
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

/**
 * Authentication Controller
 * Handles login and registration endpoints
 */
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<ApiResponse<AuthResponse>> {
        logger.info { "Login request for email: ${request.email}" }

        val authResponse = authService.login(request)

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Login successful",
                data = authResponse
            )
        )
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<ApiResponse<AuthResponse>> {
        logger.info { "Registration request for email: ${request.email}" }

        val authResponse = authService.register(request)

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Registration successful",
                data = authResponse
            )
        )
    }
}
