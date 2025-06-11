package com.nexon.board.controller.exception

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CommonException::class)
    protected fun handleCommonException(e: CommonException, response:HttpServletResponse) {
        response.characterEncoding = "UTF-8"
        response.contentType = "application/json;charset=UTF-8"
        response.status = e.exceptionCode.status.value()
        response.writer.write("${e.exceptionCode.message}")
    }
}