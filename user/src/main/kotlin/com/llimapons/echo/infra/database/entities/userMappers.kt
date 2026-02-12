package com.llimapons.echo.infra.database.entities

import com.llimapons.echo.domain.model.User

fun UserEntity.toUser(): User =
    User(
        id = this.id!!,
        email = this.email,
        username = this.username,
        hasEmailVerified = this.hasEmailVerified
    )