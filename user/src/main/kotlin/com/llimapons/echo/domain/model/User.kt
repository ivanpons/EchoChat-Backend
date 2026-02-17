package com.llimapons.echo.domain.model

import com.llimapons.echo.domain.type.UserId

data class User(
    val id: UserId,
    val username: String,
    val email: String,
    val hasEmailVerified: Boolean
)