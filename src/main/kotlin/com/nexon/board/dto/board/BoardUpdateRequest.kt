package com.nexon.board.dto.board

data class BoardUpdateRequest(
    var userId: Int,
    val boardId: Int,
    val updatedTitle: String,
    val updatedContent: String,
    val updatedCategory: String,
    val updatedNotice: Boolean
)