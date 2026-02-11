package com.llimapons.echo.database.repositories

import com.llimapons.echo.database.entities.UserEntity
import com.llimapons.echo.domain.model.UserId
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, UserId> {
    fun findByEmail(email: String): UserEntity?
    fun findByEmailOrUsername(email: String, username: String): UserEntity?
}