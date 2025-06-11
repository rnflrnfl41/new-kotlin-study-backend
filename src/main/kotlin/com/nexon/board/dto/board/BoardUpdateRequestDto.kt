package com.nexon.board.dto.board

import com.nexon.board.domain.Board
import com.nexon.board.domain.User

data class BoardUpdateRequestDto(
    var userIdx: Int,
    val boardId: Int,
    val updatedTitle: String,
    val updatedContent: String,
    val updatedCategory: String,
    val updatedNotice: Boolean
)
