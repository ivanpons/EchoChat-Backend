package com.llimapons.echo.api.dto

import com.llimapons.echo.domain.type.UserId

data class ChatParticipantDto(
    val userId: UserId,
    val username: String,
    val email: String,
    val profilePictureUrl: String?
)