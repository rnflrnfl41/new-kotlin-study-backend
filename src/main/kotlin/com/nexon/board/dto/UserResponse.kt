package com.nexon.board.dto

data class SignInResponse(
    val idx: Int?,
    val name: String,
    val userId: String,
    val token: String,
    val userProfileImg: String
)
