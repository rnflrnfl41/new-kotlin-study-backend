package com.nexon.board.controller.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime
import java.time.LocalDateTime.now

data class ErrorResponse(
    val timestamp: LocalDateTime,
    val statusCode: Int,
    val message: String,
    val status: HttpStatus
){
    constructor(status: HttpStatus,statusCode: Int ,errorMessage: String) : this(now(), statusCode, errorMessage,status)
}