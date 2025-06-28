package com.example.taskManagerController.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
        val user = org.springframework.security.core.userdetails.User.builder()
            .username("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build()

        val admin = org.springframework.security.core.userdetails.User.builder()
            .username("admin")
            .password(passwordEncoder.encode("adminpass"))
            .roles("ADMIN", "USER")
            .build()

        return InMemoryUserDetailsManager(user, admin)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/", "/api/tasks").permitAll()
                    .requestMatchers("/api/tasks/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()

            }
            .httpBasic { }
            .sessionManagement { session -> session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS) }
        return http.build()
    }
}

