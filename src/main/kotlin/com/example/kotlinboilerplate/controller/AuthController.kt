package com.example.kotlinboilerplate.controller

import com.example.kotlinboilerplate.dto.ReqAuthDto
import com.example.kotlinboilerplate.dto.ResAuthDto
import com.example.kotlinboilerplate.exception.InvalidCredentialsException
import com.example.kotlinboilerplate.service.TokenService
import com.example.kotlinboilerplate.service.UserService
import com.example.kotlinboilerplate.util.IpAddressUtil
import com.example.kotlinboilerplate.util.JwtUtil
import com.example.kotlinboilerplate.validation.SignUpValidation
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
class AuthController(
    val userService: UserService,
    val tokenService: TokenService,
    val jwtUtil: JwtUtil,
    val ipAddressUtil: IpAddressUtil
) {
    private val log = LoggerFactory.getLogger(this.javaClass)


    @PostMapping("/signin")
    fun signin(
        request: HttpServletRequest,
        @RequestBody @Validated reqAuthDto: ReqAuthDto,
        result: BindingResult
    ): ResponseEntity<ResAuthDto> {
        if (result.hasErrors()) {
            // 필드 오류 메시지만 가져오기
            val errorMessage = result.fieldErrors
                .joinToString(", ") { it.defaultMessage ?: "Unknown error" }

            // 하나의 메시지만 반환
            log.error(errorMessage)
            throw InvalidCredentialsException(errorMessage)
        }

        val userEntity = userService.signin(reqAuthDto)
        val token = jwtUtil.generateToken(userEntity.email)
        val refreshToken = jwtUtil.generateRefreshToken(userEntity.email)
        tokenService.tokenSave(refreshToken, userEntity.id!!, ipAddressUtil.getClientIp(request))


        val resAuthDto = ResAuthDto(email = userEntity.email, message = "로그인 성공!", token = token)
        return ResponseEntity.ok(resAuthDto)
    }

    @PostMapping("/signup")
    fun signup(
        request: HttpServletRequest,
        @RequestBody @Validated(SignUpValidation::class) reqAuthDto: ReqAuthDto,
        result: BindingResult
    ): ResponseEntity<ResAuthDto> {
        if (result.hasErrors()) {
            // 필드 오류 메시지만 가져오기
            val errorMessage = result.fieldErrors
                .joinToString(", ") { it.defaultMessage ?: "Unknown error" }

            // 하나의 메시지만 반환
            log.error(errorMessage)
            throw InvalidCredentialsException(errorMessage)
        }
        // ip 가져오기
        val ipAddress = ipAddressUtil.getClientIp(request)
        reqAuthDto.ipAddress = ipAddress

        val userEntity = userService.signup(reqAuthDto)
        val resAuthDto = ResAuthDto(email = userEntity.email, message = "회원가입 성공!", token = "")
        return ResponseEntity.ok(resAuthDto)
    }
}