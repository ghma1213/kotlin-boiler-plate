package com.example.kotlinboilerplate.filter

import com.example.kotlinboilerplate.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil,
): OncePerRequestFilter() {

    private val whiteList = listOf(
        "/auth/**",
        "/"
    )

    private fun isWhiteListed(request: HttpServletRequest): Boolean {
        val requestURI = request.requestURI
        // 화이트리스트 URL 패턴 확인
        return whiteList.any { pattern ->
            AntPathMatcher().match(pattern, requestURI)
        }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (!isWhiteListed(request)) {
            val token = request.getHeader("Authorization")?.removePrefix("Bearer ")
            if (!token.isNullOrEmpty() && jwtUtil.validateToken(token)) {
    //            try {
    //                val claims = jwtUtil.getClaims(token)
    //                val email = claims["email"] as String
                    println("토큰 검증!")
    //            } catch (e: ExpiredJwtException) {
    //            }
            } else {
                // 액세스 토큰이 만료된 경우, 리프레시 토큰을 사용하여 액세스 토큰을 재발급
                val refreshToken = request.getHeader("Refresh-Token")
                if (!refreshToken.isNullOrEmpty() && jwtUtil.validateRefreshToken(refreshToken)) {
                    val newToken = jwtUtil.generateToken(refreshToken)
                    response.setHeader("Authorization", "Bearer $newToken")
                } else {
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                    response.writer.write("Refresh token is required")
                    return
                }
            }
        }
        filterChain.doFilter(request, response)
    }


}