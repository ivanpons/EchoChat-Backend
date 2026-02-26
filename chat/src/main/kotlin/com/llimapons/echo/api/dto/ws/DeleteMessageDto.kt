package com.llimapons.echo.api.dto.ws

import com.llimapons.echo.domain.type.ChatId
import com.llimapons.echo.domain.type.ChatMessageId

data class DeleteMessageDto(
    val chatId: ChatId,
    val messageId: ChatMessageId
)