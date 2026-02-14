package com.llimapons.echo.service.auth

import com.llimapons.echo.api.mapper.toEmailVerificationToken
import com.llimapons.echo.domain.exception.InvalidCredentialsException
import com.llimapons.echo.domain.exception.InvalidTokenException
import com.llimapons.echo.domain.exception.SamePasswordException
import com.llimapons.echo.domain.exception.UserNotFoundException
import com.llimapons.echo.domain.model.EmailVerificationToken
import com.llimapons.echo.domain.model.UserId
import com.llimapons.echo.infra.database.entities.EmailVerificationTokenEntity
import com.llimapons.echo.infra.database.entities.PasswordResetTokenEntity
import com.llimapons.echo.infra.database.repositories.PasswordResetTokenRepository
import com.llimapons.echo.infra.database.repositories.RefreshTokenRepository
import com.llimapons.echo.infra.database.repositories.UserRepository
import com.llimapons.echo.infra.security.PasswordEncoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class PasswordResetService(
    private val passwordResetTokenRepository: PasswordResetTokenRepository,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    @param:Value("\${echo.email.reset-password.expiry-minutes}")
    private val expiryMinutes: Long
) {

    @Transactional
    fun requestPasswordReset(email: String) {
        val userEntity = userRepository.findByEmail(email) ?: return

        passwordResetTokenRepository.invalidateActiveTokensForUser(user = userEntity)

        val token = PasswordResetTokenEntity(
            expiresAt = Instant.now().plus(expiryMinutes, ChronoUnit.MINUTES),
            user = userEntity,
        )

        passwordResetTokenRepository.save(token)

        //TODO: Inform notification service about password reset trigger to send email
    }

    @Transactional
    fun resetPassword(token: String, newPassword: String) {
        val resetToken = passwordResetTokenRepository.findByToken(token)
            ?: throw InvalidTokenException("Reset Password token is invalid.")

        if (resetToken.isUsed) {
            throw InvalidTokenException("Reset Password token is already used.")
        }

        if (resetToken.isExpired) {
            throw InvalidTokenException("Reset Password token is expired.")
        }
        val user = resetToken.user
        if(passwordEncoder.matches(newPassword, user.hashedPassword)){
            throw SamePasswordException()
        }
        val hashedPassword = passwordEncoder.encode(newPassword)!!

        userRepository.save(
            resetToken.user.apply {
                 this.hashedPassword = hashedPassword
             }
         )

        passwordResetTokenRepository.save(
            resetToken.apply {
                this.usedAt = Instant.now()
            }
        )

        refreshTokenRepository.deleteByUserId(user.id!!)
    }

    @Transactional
    fun changePassword(
        userId: UserId,
        oldPassword: String,
        newPassword: String
    ) {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException()

        if(!passwordEncoder.matches(oldPassword, user.hashedPassword)){
            throw InvalidCredentialsException()
        }

        if (oldPassword == newPassword) {
            throw SamePasswordException()
        }

        refreshTokenRepository.deleteByUserId(user.id!!)

        val hashedPassword = passwordEncoder.encode(newPassword)!!
        userRepository.save(
            user.apply {
                this.hashedPassword = hashedPassword
            }
        )
    }

    @Scheduled(cron = "0 0 3 * * *")
    fun cleanupExpiredTokens(){
        passwordResetTokenRepository.deleteByExpiresAtLessThan(
            now = Instant.now()
        )
    }
}