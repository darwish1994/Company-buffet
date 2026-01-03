package com.company.beverages.exception

import com.company.beverages.model.dto.response.ErrorResponse
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger {}

/**
 * Global Exception Handler
 * Handles all exceptions and returns consistent error responses
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.error { "IllegalArgumentException: ${ex.message}" }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    error = "Bad Request",
                    message = ex.message ?: "Invalid request"
                )
            )
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<ErrorResponse> {
        logger.error { "AccessDeniedException: ${ex.message}" }

        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(
                ErrorResponse(
                    error = "Forbidden",
                    message = "You don't have permission to access this resource"
                )
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.fieldErrors.joinToString(", ") {
            "${it.field}: ${it.defaultMessage}"
        }

        logger.error { "Validation error: $errors" }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    error = "Validation Error",
                    message = errors
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error(ex) { "Unhandled exception: ${ex.message}" }

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    error = "Internal Server Error",
                    message = "An unexpected error occurred"
                )
            )
    }
}
