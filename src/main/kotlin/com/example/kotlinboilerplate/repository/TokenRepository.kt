package com.example.kotlinboilerplate.repository

import com.example.kotlinboilerplate.entity.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository: JpaRepository<TokenEntity, Long> {
    fun findByUserId(userId: Long): TokenEntity?
}