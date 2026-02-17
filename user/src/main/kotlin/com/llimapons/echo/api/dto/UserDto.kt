package com.llimapons.echo.api.dto

import com.llimapons.echo.domain.type.UserId

data class UserDto(
    val id: UserId,
    val email: String,
    val username: String,
    val hasVerifiedEmail: Boolean
)
