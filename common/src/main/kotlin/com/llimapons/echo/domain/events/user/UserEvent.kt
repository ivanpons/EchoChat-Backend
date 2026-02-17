package com.llimapons.echo.domain.events.user

import com.llimapons.echo.domain.events.EchoEvent
import com.llimapons.echo.domain.type.UserId
import java.time.Instant
import java.util.UUID

sealed class UserEvent(
    override val eventId: String = UUID.randomUUID().toString(),
    override val exchange: String = UserEventConstants.USER_EXCHANGE,
    override val occurredAt: Instant = Instant.now()
): EchoEvent {

    data class Created(
        val userId: UserId,
        val username: String,
        val email: String,
        val verificationToken: String,
        override val eventKey: String = UserEventConstants.USER_CREATED_KEY
    ): UserEvent(), EchoEvent

    data class Verified(
        val userId: UserId,
        val username: String,
        val email: String,
        override val eventKey: String = UserEventConstants.USER_VERIFIED
    ): UserEvent(), EchoEvent

    data class RequestResendVerification(
        val userId: UserId,
        val username: String,
        val email: String,
        val verificationToken: String,
        override val eventKey: String = UserEventConstants.USER_REQUEST_RESEND_VERIFICATION
    ): UserEvent(), EchoEvent

    data class RequestResetPassword(
        val userId: UserId,
        val username: String,
        val email: String,
        val passwordResetToken: String,
        val expiresInMinute: Long,
        override val eventKey: String = UserEventConstants.USER_REQUEST_RESET_PASSWORD
    ): UserEvent(), EchoEvent

}