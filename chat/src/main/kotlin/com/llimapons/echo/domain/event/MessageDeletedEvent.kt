package com.llimapons.echo.domain.event

import com.llimapons.echo.domain.type.ChatId
import com.llimapons.echo.domain.type.ChatMessageId

data class MessageDeletedEvent(
    val chatId: ChatId,
    val messageId: ChatMessageId,
)