package com.example.kotlinboilerplate.util

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class IpAddressUtil {
    fun getClientIp(request: HttpServletRequest): String {
        val header = request.getHeader("X-Forwarded-For")
        return if (header != null && header.isNotEmpty()) {
            // 여러 프록시 서버를 거쳤을 경우 첫 번째 IP 주소가 원본 클라이언트 IP
            header.split(",").first().trim()
        } else {
            // X-Forwarded-For 헤더가 없다면, 직접 요청한 IP를 사용
            request.remoteAddr
        }
    }
}