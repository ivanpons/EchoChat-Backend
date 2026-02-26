package com.llimapons.echo.domain.event

import com.llimapons.echo.domain.type.UserId

data class ProfilePictureUpdatedEvent(
    val userId: UserId,
    val newUrl: String?
)