package com.llimapons.echo.domain.exception

class SamePasswordException: RuntimeException(
    "The new password can't be the same as the old one"
)