package com.llimapons.echo.domain.model

import com.llimapons.echo.domain.type.ChatId
import com.llimapons.echo.domain.type.ChatMessageId
import java.time.Instant

data class ChatMessage(
    val id: ChatMessageId,
    val chatId: ChatId,
    val sender: ChatParticipant,
    val content: String,
    val createdAt: Instant
)