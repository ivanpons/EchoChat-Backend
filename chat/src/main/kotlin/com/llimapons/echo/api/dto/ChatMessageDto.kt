package com.llimapons.echo.api.dto

import com.llimapons.echo.domain.type.ChatId
import com.llimapons.echo.domain.type.ChatMessageId
import com.llimapons.echo.domain.type.UserId
import java.time.Instant

data class ChatMessageDto(
    val id: ChatMessageId,
    val chatId: ChatId,
    val content: String,
    val createdAt: Instant,
    val senderId: UserId
)