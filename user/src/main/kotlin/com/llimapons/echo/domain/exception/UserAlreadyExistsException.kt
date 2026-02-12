package com.llimapons.echo.domain.exception

class UserAlreadyExistsException: RuntimeException("User with that username or email already exists")