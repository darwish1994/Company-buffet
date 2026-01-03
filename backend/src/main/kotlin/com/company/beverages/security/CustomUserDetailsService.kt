package com.company.beverages.security

import com.company.beverages.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Custom UserDetailsService for loading user by ID
 */
@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(userId: String): UserDetails {
        val user = userRepository.findById(userId)
            .orElseThrow { UsernameNotFoundException("User not found with id: $userId") }

        val authorities = listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))

        return User(
            user.id,
            user.password,
            user.active,
            true,
            true,
            true,
            authorities
        )
    }

    fun loadUserByEmail(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("User not found with email: $email") }

        val authorities = listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))

        return User(
            user.id,
            user.password,
            user.active,
            true,
            true,
            true,
            authorities
        )
    }
}
