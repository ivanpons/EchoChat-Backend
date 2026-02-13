package com.llimapons.echo.service.auth

import com.llimapons.echo.api.mapper.toEmailVerificationToken
import com.llimapons.echo.domain.exception.InvalidTokenException
import com.llimapons.echo.domain.exception.UserNotFoundException
import com.llimapons.echo.domain.model.EmailVerificationToken
import com.llimapons.echo.infra.database.entities.EmailVerificationTokenEntity
import com.llimapons.echo.infra.database.repositories.EmailVerificationTokenRepository
import com.llimapons.echo.infra.database.repositories.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

@Service
class EmailVerificationService(
    private val emailVerificationTokenRepository: EmailVerificationTokenRepository,
    private val userRepository: UserRepository,
    @param:Value("\${echo.email.verification.expiry-hours}") private val expiryHours: Long
) {


    @Transactional
    fun createVerificationToken(email: String): EmailVerificationToken {
        val userEntity = userRepository.findByEmail(email)
            ?: throw UserNotFoundException()
        val existingTokens = emailVerificationTokenRepository.findByUserAndUsedAtIsNotNull(
            user = userEntity
        )

        val now = Instant.now()
        val usedTokens = existingTokens.map {
            it.apply {
                this.usedAt = now
            }
        }

        emailVerificationTokenRepository.saveAll(usedTokens)

        val token = EmailVerificationTokenEntity(
            expiresAt = now.plus(expiryHours, ChronoUnit.HOURS),
            user = userEntity,
        )

        return emailVerificationTokenRepository.save(token).toEmailVerificationToken()
    }

    @Transactional
    fun verifyEmail(token: String) {
        val verificationToken = emailVerificationTokenRepository.findByToken(token)
            ?: throw InvalidTokenException("Email verification token is invalid.")

        if (verificationToken.isUsed) {
            throw InvalidTokenException("Email verification token is already used.")
        }

        if (verificationToken.isExpired) {
            throw InvalidTokenException("Email verification token is expired.")
        }

        emailVerificationTokenRepository.save(
            verificationToken.apply {
                this.usedAt = Instant.now()
            }
        )

        userRepository.save(
             verificationToken.user.apply {
                 this.hasEmailVerified = true
             }
         )
    }


    @Scheduled(cron = "0 0 3 * * *")
    fun cleanupExpiredTokens(){
        emailVerificationTokenRepository.deleteByExpiresAtLessThan(
            now = Instant.now()
        )
    }
}