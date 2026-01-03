package com.company.beverages.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

/**
 * WebSocket Configuration
 * Enables real-time communication for order updates
 */
@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        // Enable simple broker for broadcasting messages
        config.enableSimpleBroker("/topic", "/queue")

        // Application destination prefix for sending messages
        config.setApplicationDestinationPrefixes("/app")

        // User destination prefix for sending user-specific messages
        config.setUserDestinationPrefix("/user")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // Register STOMP endpoint with SockJS fallback
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
            .withSockJS()
    }
}
