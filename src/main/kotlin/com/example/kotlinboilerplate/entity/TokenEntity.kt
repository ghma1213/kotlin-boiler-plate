package com.example.kotlinboilerplate.entity

import jakarta.persistence.*

@Entity
@Table(name = "token")
data class TokenEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false, unique = true)
    val refreshToken: String,
    @Column(nullable = false, unique = true)
    val userId: Long,
    @Column(name = "ip_address")
    val ipAddress: String
): BaseEntity() {
    fun updateId(oldToken: TokenEntity) {
        this.id = oldToken.id
    }
}