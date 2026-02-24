package com.llimapons.echo.domain.exception

import com.llimapons.echo.domain.type.ChatMessageId

class MessageNotFoundException(
    private val id: ChatMessageId
): RuntimeException(
    "Message with ID $id not found"
)