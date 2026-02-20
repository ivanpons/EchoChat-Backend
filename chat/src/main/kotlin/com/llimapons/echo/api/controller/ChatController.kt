package com.llimapons.echo.api.controller

import com.llimapons.echo.api.dto.ChatDto
import com.llimapons.echo.api.dto.CreateChatRequest
import com.llimapons.echo.api.mappers.toChatDto
import com.llimapons.echo.api.utils.requestUserId
import com.llimapons.echo.service.ChatService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatController(
    private val chatService: ChatService
) {

    @PostMapping
    fun createChat(
        @Valid @RequestBody body: CreateChatRequest
    ): ChatDto {
        return chatService.createChat(
            creatorId = requestUserId,
            otherUserIds = body.otherUserIds.toSet()
        ).toChatDto()
    }
}