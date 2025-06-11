package com.nexon.board.config

import com.nexon.board.controller.exception.CommonException
import com.nexon.board.controller.exception.CommonExceptionCode
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.client.HttpClientErrorException.Unauthorized
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    @Lazy private val jwtTokenProvider: TokenProvider,
    private val securityProperties: SecurityProperties
    ): GenericFilterBean() {

    private val antPathMatcher = AntPathMatcher()
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        // 요청 URL이 제외 경로에 포함되는지 검사
        val requestPath = httpRequest.requestURI
        if (securityProperties.permitAllPaths.any { antPathMatcher.match(it, requestPath) }) {
            chain.doFilter(request, response) // 필터를 건너뜀
            return
        }

        try {
            val token: String = jwtTokenProvider.resolveToken((httpRequest))

            if (!jwtTokenProvider.validateToken(token)) {
                sendUnauthorizedResponse(httpResponse)
                return
            }else{
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                val authentication = jwtTokenProvider.getAuthentication(token)
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().authentication = authentication
            }

        }catch (e: Exception){
            sendUnauthorizedResponse(httpResponse)
        }

        chain.doFilter(request, response)
    }

    private fun sendUnauthorizedResponse(httpResponse: HttpServletResponse) {
        if (!httpResponse.isCommitted) {
            httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
        }
    }

}