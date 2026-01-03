package com.company.beverages.service

import com.company.beverages.model.dto.request.LoginRequest
import com.company.beverages.model.dto.request.RegisterRequest
import com.company.beverages.model.dto.response.AuthResponse
import com.company.beverages.model.dto.response.UserResponse
import com.company.beverages.model.entity.User
import com.company.beverages.repository.UserRepository
import com.company.beverages.security.JwtTokenProvider
import mu.KotlinLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

/**
 * Authentication Service
 * Handles user login and registration
 */
@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {

    /**
     * Authenticate user and generate JWT token
     */
    fun login(request: LoginRequest): AuthResponse {
        logger.info { "Login attempt for email: ${request.email}" }

        val user = userRepository.findByEmail(request.email)
            .orElseThrow { throw IllegalArgumentException("Invalid email or password") }

        if (!user.active) {
            throw IllegalArgumentException("User account is deactivated")
        }

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("Invalid email or password")
        }

        val token = jwtTokenProvider.generateToken(user)
        logger.info { "User logged in successfully: ${user.email}" }

        return AuthResponse(
            token = token,
            user = mapToUserResponse(user)
        )
    }

    /**
     * Register new user
     */
    fun register(request: RegisterRequest): AuthResponse {
        logger.info { "Registration attempt for email: ${request.email}" }

        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        request.employeeId?.let {
            if (userRepository.existsByEmployeeId(it)) {
                throw IllegalArgumentException("Employee ID already exists")
            }
        }

        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = request.role,
            department = request.department,
            employeeId = request.employeeId,
            quota = request.quota
        )

        val savedUser = userRepository.save(user)
        val token = jwtTokenProvider.generateToken(savedUser)

        logger.info { "User registered successfully: ${savedUser.email}" }

        return AuthResponse(
            token = token,
            user = mapToUserResponse(savedUser)
        )
    }

    private fun mapToUserResponse(user: User) = UserResponse(
        id = user.id!!,
        name = user.name,
        email = user.email,
        role = user.role,
        department = user.department,
        employeeId = user.employeeId,
        quota = user.quota,
        active = user.active
    )
}
