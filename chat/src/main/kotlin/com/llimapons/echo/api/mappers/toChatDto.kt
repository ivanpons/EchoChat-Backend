package com.llimapons.echo.api.mappers

import com.llimapons.echo.api.dto.ChatDto
import com.llimapons.echo.api.dto.ChatMessageDto
import com.llimapons.echo.api.dto.ChatParticipantDto
import com.llimapons.echo.domain.model.Chat
import com.llimapons.echo.domain.model.ChatMessage
import com.llimapons.echo.domain.model.ChatParticipant

fun Chat.toChatDto(): ChatDto {
    return ChatDto(
        id = id,
        participants = participants.map {
            it.toChatParticipantDto()
        },
        lastActivityAt = lastActivityAt,
        lastMessage = lastMessage?.toChatMessageDto(),
        creator = creator.toChatParticipantDto()
    )
}

fun ChatMessage.toChatMessageDto(): ChatMessageDto {
    return ChatMessageDto(
        id = id,
        chatId = chatId,
        content = content,
        createdAt = createdAt,
        senderId = sender.userId
    )
}

fun ChatParticipant.toChatParticipantDto(): ChatParticipantDto {
    return ChatParticipantDto(
        userId = userId,
        username = username,
        email = email,
        profilePictureUrl = profilePictureUrl
    )
}