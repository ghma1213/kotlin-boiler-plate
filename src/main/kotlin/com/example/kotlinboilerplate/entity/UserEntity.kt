package com.example.kotlinboilerplate.entity

import jakarta.persistence.*


@Entity
@Table(name = "user")
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    val email: String,
    val password: String,
    val name: String,
    @Column(name = "ip_address")
    val ipAddress: String
): BaseEntity()