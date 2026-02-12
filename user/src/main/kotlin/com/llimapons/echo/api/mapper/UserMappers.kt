package com.llimapons.echo.api.mapper

import com.llimapons.echo.api.dto.AuthenticatedUserDto
import com.llimapons.echo.api.dto.UserDto
import com.llimapons.echo.domain.model.AuthenticatedUser
import com.llimapons.echo.domain.model.User

fun AuthenticatedUser.toAuthenticatedUserDto(): AuthenticatedUserDto =
    AuthenticatedUserDto(
        user = this.user.toUserDto(),
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )

fun User.toUserDto(): UserDto =
    UserDto(
        id = this.id,
        email = this.email,
        username = this.username,
        hasVerifiedEmail = this.hasEmailVerified
    )