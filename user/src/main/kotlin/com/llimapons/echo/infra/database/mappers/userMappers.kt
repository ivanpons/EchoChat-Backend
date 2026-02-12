package com.llimapons.echo.infra.database.mappers

import com.llimapons.echo.domain.model.User
import com.llimapons.echo.infra.database.entities.UserEntity

fun UserEntity.toUser(): User =
    User(
        id = this.id!!,
        email = this.email,
        username = this.username,
        hasEmailVerified = this.hasEmailVerified
    )