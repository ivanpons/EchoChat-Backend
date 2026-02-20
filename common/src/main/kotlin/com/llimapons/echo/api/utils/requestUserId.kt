package com.llimapons.echo.api.utils

import com.llimapons.echo.domain.exception.UnauthorizedException
import com.llimapons.echo.domain.type.UserId
import org.springframework.security.core.context.SecurityContextHolder

val requestUserId: UserId
    get() = SecurityContextHolder.getContext().authentication?.principal as? UserId
        ?: throw UnauthorizedException()