package com.nexon.board.controller.exception

import org.springframework.http.HttpStatus

enum class CommonExceptionCode(status: HttpStatus, message: String) {
    DUPLICATE_ID(HttpStatus.BAD_REQUEST, "중복된 아이디입니다."),
    ID_PASSWORD_FAIL(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 옳바르지않습니다."),
    NO_USER_BY_ID(HttpStatus.BAD_REQUEST, "일치하는 유저가 없습니다."),
    SAVE_FAILED(HttpStatus.BAD_REQUEST, "회원가입에 실패했습니다."),
    NO_USER(HttpStatus.NOT_FOUND, "유저 찾을 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "토큰이 없거나 유효하지 않습니다."),
    NO_BOARD(HttpStatus.NO_CONTENT, "해당 카테고리의 게시글이 없습니다."),
    NO_CATEGORY(HttpStatus.NO_CONTENT, "해당 카테고리를 찾을수 없습니다."),
    NO_FILEPATH(HttpStatus.NO_CONTENT, "파일 경로를 찾을수 없습니다."),
    NO_AUTH(HttpStatus.UNAUTHORIZED, "해당 유저는 권한이 없습니다."),
    NO_SUB_COMMENT(HttpStatus.UNAUTHORIZED, "해당 대댓글을 찾을수 없습니다."),
    NO_COMMENT(HttpStatus.NO_CONTENT, "해당 댓글을 찾을수 없습니다.");
    val status: HttpStatus = status
    val message: String = message

}