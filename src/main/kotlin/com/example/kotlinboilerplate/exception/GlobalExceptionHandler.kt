package com.example.kotlinboilerplate.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    // 커스텀 예외 처리 (예: Not Found)
    @ExceptionHandler(NotFoundException::class)
    fun handleCustomNotFoundException(ex: NotFoundException): ResponseEntity<ResException> {
        val resException = ResException(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message ?: "찾을 수 없습니다."
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resException)
    }

    // 다른 커스텀 예외 처리 (예: Invalid Credentials)
    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(ex: InvalidCredentialsException): ResponseEntity<ResException> {
        val resException = ResException(
            status = HttpStatus.UNAUTHORIZED.value(),
            message = ex.message ?: "권한이 없습니다."
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resException)
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ResException> {
        val resException = ResException(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = ex.message ?: "서버에 문제가 발생했습니다."
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resException)
    }
}