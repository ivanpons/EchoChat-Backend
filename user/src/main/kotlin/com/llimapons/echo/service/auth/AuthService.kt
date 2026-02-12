package com.llimapons.echo.service.auth

import com.llimapons.echo.domain.exception.UserAlreadyExistsException
import com.llimapons.echo.domain.model.User
import com.llimapons.echo.infra.database.entities.UserEntity
import com.llimapons.echo.infra.database.entities.toUser
import com.llimapons.echo.infra.database.repositories.UserRepository
import com.llimapons.echo.infra.security.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
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
}