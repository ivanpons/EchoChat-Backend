package com.llimapons.echo.api.dto.ws

import com.llimapons.echo.domain.type.UserId

data class ProfilePictureUpdateDto(
    val userId: UserId,
    val newUrl: String?
)