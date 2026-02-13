package com.llimapons.echo.infra.database.repositories

import com.llimapons.echo.infra.database.entities.EmailVerificationTokenEntity
import com.llimapons.echo.infra.database.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

interface EmailVerificationTokenRepository: JpaRepository<EmailVerificationTokenEntity, Long> {
    fun findByToken(token: String): EmailVerificationTokenEntity?
    fun deleteByExpiresAtLessThan(now: Instant)
    fun findByUserAndUsedAtIsNotNull(user: UserEntity): List<EmailVerificationTokenEntity>
}