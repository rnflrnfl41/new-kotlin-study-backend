package com.nexon.board.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000")  // 허용할 클라이언트 도메인
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")  // 허용할 HTTP 메서드
            .allowedHeaders("*")  // 허용할 헤더
            .allowCredentials(true)  // 자격 증명(쿠키, 인증 정보 등) 허용
    }
}