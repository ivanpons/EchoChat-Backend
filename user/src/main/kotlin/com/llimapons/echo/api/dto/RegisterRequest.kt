package com.llimapons.echo.api.dto

import com.llimapons.echo.api.util.Password
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

data class RegisterRequest(
    @field:Email(message = "Invalid email")
    val email: String,
    @field:Length(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    val username: String,
    @field:Password
    val password: String
)
