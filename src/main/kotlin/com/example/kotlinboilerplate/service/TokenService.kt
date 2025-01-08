package com.example.kotlinboilerplate.service

import com.example.kotlinboilerplate.entity.TokenEntity
import com.example.kotlinboilerplate.repository.TokenRepository
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val tokenRepository: TokenRepository
) {
    fun tokenSave(refreshToken: String, userId: Long, ipAddress: String) {
        val oldToken = tokenRepository.findByUserId(userId)
        val token = TokenEntity(refreshToken = refreshToken, userId = userId, ipAddress = ipAddress)
        if (oldToken != null) {
            token.updateId(oldToken = oldToken)
        }
        tokenRepository.save(token)
    }
}