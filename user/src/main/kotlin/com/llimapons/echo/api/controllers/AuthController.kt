package com.llimapons.echo.api.controllers

import com.llimapons.echo.api.dto.AuthenticatedUserDto
import com.llimapons.echo.api.dto.ChangePasswordRequest
import com.llimapons.echo.api.dto.EmailRequest
import com.llimapons.echo.api.dto.LoginRequest
import com.llimapons.echo.api.dto.RefreshRequest
import com.llimapons.echo.api.dto.RegisterRequest
import com.llimapons.echo.api.dto.ResetPasswordRequest
import com.llimapons.echo.api.dto.UserDto
import com.llimapons.echo.api.mapper.toAuthenticatedUserDto
import com.llimapons.echo.api.mapper.toUserDto
import com.llimapons.echo.service.auth.AuthService
import com.llimapons.echo.service.auth.EmailVerificationService
import com.llimapons.echo.service.auth.PasswordResetService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val emailVerificationService: EmailVerificationService,
    private val passwordResetService: PasswordResetService
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

    @PostMapping("/refresh")
    fun refresh(
        @Valid @RequestBody body: RefreshRequest
    ): AuthenticatedUserDto {
        return authService.refresh(
            refreshToken = body.refreshToken
        ).toAuthenticatedUserDto()
    }

    @PostMapping("/logout")
    fun logout(
        @Valid @RequestBody body: RefreshRequest
    ) {
        authService.logout(
            refreshToken = body.refreshToken
        )
    }

    @GetMapping("/verify")
    fun verifyEmail(
        @RequestParam token: String
    ){
        emailVerificationService.verifyEmail(token)
    }

    @PostMapping("/reset-password")
    fun resetPassword(
        @Valid @RequestBody body: ResetPasswordRequest
    ) {
       passwordResetService.resetPassword(
           token = body.token,
           newPassword = body.newPassword
       )
    }

    @PostMapping("/forgot-password")
    fun forgotPassword(
        @Valid @RequestBody body: EmailRequest
    ) {
        passwordResetService.requestPasswordReset(
            email = body.email
        )
    }

    @PostMapping("/change-password")
    fun changePassword(
        @Valid @RequestBody body: ChangePasswordRequest
    ) {
        //TODO: Extract request userId and call service
//        passwordResetService.changePassword(
//            newPassword = body.newPassword,
//            oldPassword = body.oldPassword,
//            userId =
//        )
    }

}