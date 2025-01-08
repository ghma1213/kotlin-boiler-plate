package com.example.kotlinboilerplate.dto

import com.example.kotlinboilerplate.validation.SignUpValidation
import jakarta.validation.constraints.NotEmpty

data class ReqAuthDto (
    @field:NotEmpty(message = "이메일을 입력해주세요.")
    val email: String,
    @field:NotEmpty(message = "비밀번호를 입력해주세요.")
    val password: String,
    @field:NotEmpty(message = "이름을 입력해주세요.", groups = [SignUpValidation::class])
    val name: String?,
    var ipAddress: String?
)