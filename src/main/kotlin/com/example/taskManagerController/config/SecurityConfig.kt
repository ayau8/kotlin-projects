package com.example.taskManagerController.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.core.userdetails.User

@Configuration
@EnableWebSecurity
class SecurityConfig {

    /**
     * Provides a BCrypt-based password encoder for hashing user passwords.
     *
     * @return A PasswordEncoder instance using the BCrypt algorithm.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * Creates an in-memory user details service with predefined users for authentication.
     *
     * Defines two users: one with the role "USER" and another with roles "ADMIN" and "USER".
     *
     * @param passwordEncoder The encoder used to hash user passwords.
     * @return An in-memory user details manager containing the predefined users.
     */
    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
        val user = User.builder()
            .username("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build()

        val admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("adminpass"))
            .roles("ADMIN", "USER")
            .build()

        return InMemoryUserDetailsManager(user, admin)
    }

    /**
     * Configures the application's HTTP security filter chain.
     *
     * Sets up authorization rules for various endpoints, enables HTTP Basic authentication, disables CSRF protection, and enforces stateless session management.
     *
     * @return The configured SecurityFilterChain.
     */
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .requestMatchers(HttpMethod.GET,"/", "/api/tasks").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/tasks").hasRole("USER")
                    .requestMatchers("/api/tasks/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .httpBasic { }
            .sessionManagement { session -> session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS) }
        return http.build()
    }
}

