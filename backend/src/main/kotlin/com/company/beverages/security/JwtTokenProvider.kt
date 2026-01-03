package com.company.beverages.security

import com.company.beverages.model.entity.User
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

private val logger = KotlinLogging.logger {}

/**
 * JWT Token Provider for generating and validating JWT tokens
 */
@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}")
    private val jwtSecret: String,

    @Value("\${jwt.expiration}")
    private val jwtExpiration: Long
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    /**
     * Generate JWT token for authenticated user
     */
    fun generateToken(user: User): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtExpiration)

        val claims = Jwts.claims().setSubject(user.id)
        claims["userId"] = user.id
        claims["email"] = user.email
        claims["role"] = user.role.name
        claims["name"] = user.name

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    /**
     * Get user ID from JWT token
     */
    fun getUserIdFromToken(token: String): String? {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body

            claims.subject
        } catch (e: Exception) {
            logger.error { "Failed to extract user ID from token: ${e.message}" }
            null
        }
    }

    /**
     * Validate JWT token
     */
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: SecurityException) {
            logger.error { "Invalid JWT signature: ${e.message}" }
            false
        } catch (e: MalformedJwtException) {
            logger.error { "Invalid JWT token: ${e.message}" }
            false
        } catch (e: ExpiredJwtException) {
            logger.error { "Expired JWT token: ${e.message}" }
            false
        } catch (e: UnsupportedJwtException) {
            logger.error { "Unsupported JWT token: ${e.message}" }
            false
        } catch (e: IllegalArgumentException) {
            logger.error { "JWT claims string is empty: ${e.message}" }
            false
        }
    }
}
