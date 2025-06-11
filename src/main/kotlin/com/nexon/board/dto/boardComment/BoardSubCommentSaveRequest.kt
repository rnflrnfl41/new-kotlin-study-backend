package com.nexon.board.dto.boardComment

data class BoardSubCommentSaveRequest(
        val commentIdx: Int,
        val subComment: String,
        val relativeUserIdx: Int,
        val fileUrl: String?,
)
