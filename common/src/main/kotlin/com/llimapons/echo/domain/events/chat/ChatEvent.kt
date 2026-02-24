package com.llimapons.echo.domain.events.chat

import com.llimapons.echo.domain.events.EchoEvent
import com.llimapons.echo.domain.type.ChatId
import com.llimapons.echo.domain.type.UserId
import java.time.Instant
import java.util.*

sealed class ChatEvent(
    override val eventId: String = UUID.randomUUID().toString(),
    override val exchange: String = ChatEventConstants.CHAT_EXCHANGE,
    override val occurredAt: Instant = Instant.now(),
): EchoEvent {

    data class NewMessage(
        val senderId: UserId,
        val senderUsername: String,
        val recipientIds: Set<UserId>,
        val chatId: ChatId,
        val message: String,
        override val eventKey: String = ChatEventConstants.CHAT_NEW_MESSAGE
    ): ChatEvent(), EchoEvent
}