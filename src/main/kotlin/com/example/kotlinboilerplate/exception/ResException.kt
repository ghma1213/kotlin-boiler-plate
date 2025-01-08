package com.example.kotlinboilerplate.exception

import java.time.LocalDateTime

data class ResException(val timestamp: LocalDateTime = LocalDateTime.now(),
                        val status: Int,
                        val message: String)