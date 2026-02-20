package com.llimapons.echo.domain.exception

class UnauthorizedException(
): RuntimeException(
    "Missing auth details"
)