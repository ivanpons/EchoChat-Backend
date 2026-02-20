package com.llimapons.echo.domain.model

import com.llimapons.echo.domain.type.UserId

data class ChatParticipant(
    val userId: UserId,
    val username: String,
    val email: String,
    val profilePictureUrl: String?
)