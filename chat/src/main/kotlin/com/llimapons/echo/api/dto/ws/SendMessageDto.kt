package com.llimapons.echo.api.dto.ws

import com.llimapons.echo.domain.type.ChatId
import com.llimapons.echo.domain.type.ChatMessageId

data class SendMessageDto(
    val chatId: ChatId,
    val content: String,
    val messageId: ChatMessageId? = null
)