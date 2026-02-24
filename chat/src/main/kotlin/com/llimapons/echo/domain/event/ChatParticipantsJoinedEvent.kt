package com.llimapons.echo.domain.event

import com.llimapons.echo.domain.type.ChatId
import com.llimapons.echo.domain.type.UserId

data class ChatParticipantsJoinedEvent(
    val chatId: ChatId,
    val userIds: Set<UserId>
)