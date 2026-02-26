package com.llimapons.echo.api.mappers

import com.llimapons.echo.api.dto.PictureUploadResponse
import com.llimapons.echo.domain.model.ProfilePictureUploadCredentials

fun ProfilePictureUploadCredentials.toResponse(): PictureUploadResponse {
    return PictureUploadResponse(
        uploadUrl = uploadUrl,
        publicUrl = publicUrl,
        headers = headers,
        expiresAt = expiresAt
    )
}