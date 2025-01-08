package com.example.kotlinboilerplate.service

import com.example.kotlinboilerplate.dto.ReqAuthDto
import com.example.kotlinboilerplate.entity.UserEntity
import com.example.kotlinboilerplate.exception.InvalidCredentialsException
import com.example.kotlinboilerplate.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) {
    fun signin(reqAuthDto: ReqAuthDto): UserEntity {
        val user: UserEntity = userRepository.findByEmail(reqAuthDto.email) ?: throw InvalidCredentialsException("아이디나 비밀번호를 확인해주세요.")

        // 비밀번호 비교
        if (!passwordEncoder.matches(reqAuthDto.password, user.password)) {
            throw InvalidCredentialsException("아이디나 비밀번호를 확인해주세요.")
        }

        return user
    }

    fun signup(reqAuthDto: ReqAuthDto): UserEntity {
        val email = reqAuthDto.email
        val encodedPassword = passwordEncoder.encode(reqAuthDto.password)
        val name: String = reqAuthDto.name!!
        val ipAddress = reqAuthDto.ipAddress!!
        val user = UserEntity(email = email, password = encodedPassword, name = name, ipAddress = ipAddress)
        return userRepository.save(user)
    }

}