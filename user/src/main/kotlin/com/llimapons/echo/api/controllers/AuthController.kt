package com.llimapons.echo.api.controllers

import com.llimapons.echo.api.dto.AuthenticatedUserDto
import com.llimapons.echo.api.dto.LoginRequest
import com.llimapons.echo.api.dto.RegisterRequest
import com.llimapons.echo.api.dto.UserDto
import com.llimapons.echo.api.mapper.toAuthenticatedUserDto
import com.llimapons.echo.api.mapper.toUserDto
import com.llimapons.echo.service.auth.AuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody body: RegisterRequest
    ): UserDto{
        return authService.register(
            email = body.email,
            username = body.username,
            password = body.password
        ).toUserDto()
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody body: LoginRequest
    ): AuthenticatedUserDto {
        return authService.login(
            email = body.email,
            password = body.password
        ).toAuthenticatedUserDto()
    }

}