package com.llimapons.echo.infra.messaging

import com.llimapons.echo.domain.events.user.UserEvent
import com.llimapons.echo.domain.model.ChatParticipant
import com.llimapons.echo.infra.message_queue.MessageQueues
import com.llimapons.echo.service.ChatParticipantService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ChatUserEventListener(
    private val chatParticipantService: ChatParticipantService
) {

    @RabbitListener(
        queues = [MessageQueues.CHAT_USER_EVENTS],
        containerFactory = "rabbitListenerContainerFactory")
    @Transactional
    fun handlerUserEvent(event: UserEvent){
        when(event){
            is UserEvent.Verified -> {
                chatParticipantService.createChatParticipant(
                    chatParticipant = ChatParticipant(
                        userId = event.userId,
                        email = event.email,
                        username = event.username,
                        profilePictureUrl = null
                    )
                )
            }
            else -> Unit
        }
    }
}