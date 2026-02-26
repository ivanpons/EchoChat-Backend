package com.llimapons.echo.domain.exception

class InvalidTokenException(
    override val message: String?
): RuntimeException(
    message ?: "Invalid Token"
)