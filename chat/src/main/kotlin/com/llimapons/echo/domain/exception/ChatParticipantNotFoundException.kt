package com.llimapons.echo.domain.exception

import com.llimapons.echo.domain.type.UserId

class ChatParticipantNotFoundException(
    private val id: UserId
): RuntimeException(
    "The chat participant with the ID $id was not found."
)