package com.llimapons.echo.domain.exception

class StorageException(
    override val message: String?
): RuntimeException(message ?: "Unable to store file")