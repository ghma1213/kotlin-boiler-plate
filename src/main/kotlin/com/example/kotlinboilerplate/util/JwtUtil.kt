package com.example.kotlinboilerplate.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(
    @Value("\${jwt.secret-key}") private val secretKey: String,
    @Value("\${jwt.expiration}") private val expiration: Long,
    @Value("\${jwt.refresh-secret-key}") private val refreshSecretKey: String,
    @Value("\${jwt.refresh-expiration}") private val refreshExpiration: Long
) {
    val key: SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
    val refreshKey: SecretKey = Keys.hmacShaKeyFor(refreshSecretKey.toByteArray(StandardCharsets.UTF_8))

    // 한국 시간대에 맞는 Date 객체를 생성하는 함수

    fun generateToken(email: String): String {
        val claims = mapOf("email" to email)
        return Jwts.builder()
            .claims(claims)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expiration * 1000))
            .signWith(key)
            .compact()
    }

    fun generateRefreshToken(email: String): String {
        val claims = mapOf("email" to email)
        return Jwts.builder()
            .claims(claims)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + refreshExpiration * 1000))
            .signWith(refreshKey)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaims(token, key)
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun validateRefreshToken(token: String): Boolean {
        return try {
            val claims = getClaims(token, refreshKey)
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun getClaims(token: String, key: SecretKey): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}
