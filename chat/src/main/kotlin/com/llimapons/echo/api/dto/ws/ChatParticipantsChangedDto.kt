package com.llimapons.echo.api.dto.ws

import com.llimapons.echo.domain.type.ChatId

data class ChatParticipantsChangedDto(
    val chatId: ChatId
)