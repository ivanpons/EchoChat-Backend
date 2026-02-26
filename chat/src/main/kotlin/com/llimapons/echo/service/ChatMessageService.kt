package com.llimapons.echo.service

import com.llimapons.echo.api.dto.ChatMessageDto
import com.llimapons.echo.api.mappers.toChatMessageDto
import com.llimapons.echo.domain.event.MessageDeletedEvent
import com.llimapons.echo.domain.events.chat.ChatEvent
import com.llimapons.echo.domain.exception.ChatNotFoundException
import com.llimapons.echo.domain.exception.ChatParticipantNotFoundException
import com.llimapons.echo.domain.exception.ForbiddenException
import com.llimapons.echo.domain.exception.MessageNotFoundException
import com.llimapons.echo.domain.model.ChatMessage
import com.llimapons.echo.domain.type.ChatId
import com.llimapons.echo.domain.type.ChatMessageId
import com.llimapons.echo.domain.type.UserId
import com.llimapons.echo.infra.database.entities.ChatMessageEntity
import com.llimapons.echo.infra.database.mappers.toChatMessage
import com.llimapons.echo.infra.database.repositories.ChatMessageRepository
import com.llimapons.echo.infra.database.repositories.ChatParticipantRepository
import com.llimapons.echo.infra.database.repositories.ChatRepository
import com.llimapons.echo.infra.message_queue.EventPublisher
import org.springframework.cache.annotation.CacheEvict
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class ChatMessageService(
    private val chatRepository: ChatRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val chatParticipantRepository: ChatParticipantRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val eventPublisher: EventPublisher
) {

    @Transactional
    @CacheEvict(
        value = ["messages"],
        key = "#chatId",
    )
    fun sendMessage(
        chatId: ChatId,
        senderId: UserId,
        content: String,
        messageId: ChatMessageId? = null
    ): ChatMessage {
        val chat = chatRepository.findChatById(chatId, senderId)
            ?: throw ChatNotFoundException()
        val sender = chatParticipantRepository.findByIdOrNull(senderId)
            ?: throw ChatParticipantNotFoundException(senderId)

        val savedMessage = chatMessageRepository.saveAndFlush(
            ChatMessageEntity(
                id = messageId,
                content = content.trim(),
                chatId = chatId,
                chat = chat,
                sender = sender
            )
        )

        eventPublisher.publish(
            event = ChatEvent.NewMessage(
                senderId = sender.userId,
                senderUsername = sender.username,
                recipientIds = chat.participants.map { it.userId }.toSet(),
                chatId = chatId,
                message = savedMessage.content
            )
        )

        return savedMessage.toChatMessage()
    }

    @Transactional
    fun deleteMessage(
        messageId: ChatMessageId,
        requestUserId: UserId
    ) {
        val message = chatMessageRepository.findByIdOrNull(messageId)
            ?: throw MessageNotFoundException(messageId)

        if(message.sender.userId != requestUserId) {
            throw ForbiddenException()
        }

        chatMessageRepository.delete(message)

        applicationEventPublisher.publishEvent(
            MessageDeletedEvent(
                chatId = message.chatId,
                messageId = messageId
            )
        )

        evictMessagesCache(message.chatId)
    }

    @CacheEvict(
        value = ["messages"],
        key = "#chatId",
    )
    fun evictMessagesCache(chatId: ChatId) {
        // NO-OP: Let Spring handle the cache evict
    }
}