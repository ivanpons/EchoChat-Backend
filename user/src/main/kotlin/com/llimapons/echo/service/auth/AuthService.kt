package com.llimapons.echo.service.auth

import com.llimapons.echo.domain.exception.InvalidCredentialsException
import com.llimapons.echo.domain.exception.UserAlreadyExistsException
import com.llimapons.echo.domain.exception.UserNotFoundException
import com.llimapons.echo.domain.model.AuthenticatedUser
import com.llimapons.echo.domain.model.User
import com.llimapons.echo.domain.model.UserId
import com.llimapons.echo.infra.database.entities.RefreshTokenEntity
import com.llimapons.echo.infra.database.entities.UserEntity
import com.llimapons.echo.infra.database.mappers.toUser
import com.llimapons.echo.infra.database.repositories.RefreshTokenRepository
import com.llimapons.echo.infra.database.repositories.UserRepository
import com.llimapons.echo.infra.security.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.time.Instant
import java.util.Base64

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtService: JwtService
) {
    fun register(email: String, username: String, password: String): User {
        val user = userRepository.findByEmailOrUsername(
            email = email.trim(),
            username = username.trim()
        )
        if (user != null) throw UserAlreadyExistsException()

        val savedUser = userRepository.save(
            UserEntity(
                email = email.trim(),
                username = username.trim(),
                hashedPassword = passwordEncoder.encode(password)!!
            )
        ).toUser()

        return savedUser
    }

    fun login(
        email: String,
        password: String
    ): AuthenticatedUser{
        val user = userRepository.findByEmail(email.trim())
            ?: throw InvalidCredentialsException()

        if (!passwordEncoder.matches(password, user.hashedPassword)) {
            throw InvalidCredentialsException()
        }

        //TODO verify email


        return user.id?.let { userId ->
            val accessToken = jwtService.generateAccessToken(userId)
            val refreshToken = jwtService.generateRefreshToken(userId)

            storeRefreshToken(userId, refreshToken)

            AuthenticatedUser(
                user = user.toUser(),
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } ?: throw UserNotFoundException()
    }

    private fun storeRefreshToken(userId: UserId, token: String) {
        val hashed = hashToken(token)
        val expiryMs = jwtService.refreshTokenValidityMs
        val expiresAt = Instant.now().plusMillis(expiryMs)

        refreshTokenRepository.save(
            RefreshTokenEntity(
                userId = userId,
                hashedToken = hashed,
                expiredAt = expiresAt
            )
        )

    }

    private fun hashToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashByte = digest.digest(token.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashByte)
    }

}