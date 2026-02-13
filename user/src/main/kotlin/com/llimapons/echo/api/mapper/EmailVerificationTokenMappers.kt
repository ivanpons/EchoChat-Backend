package com.llimapons.echo.api.mapper

import com.llimapons.echo.domain.model.EmailVerificationToken
import com.llimapons.echo.infra.database.entities.EmailVerificationTokenEntity
import com.llimapons.echo.infra.database.mappers.toUser

fun EmailVerificationTokenEntity.toEmailVerificationToken(): EmailVerificationToken =
    EmailVerificationToken(
        id = id,
        token = token,
        user = user.toUser()
    )