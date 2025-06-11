package com.nexon.board.controller.exception

class CommonException(exceptionCode: CommonExceptionCode) : RuntimeException(){
    val exceptionCode: CommonExceptionCode = exceptionCode
}