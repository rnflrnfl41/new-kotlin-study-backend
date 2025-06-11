package com.nexon.board.dto.boardComment

import com.nexon.board.domain.Board
import com.nexon.board.domain.BoardComments
import com.nexon.board.domain.User

data class BoardCommentsRequestDto (
    var user: User,
    var board: Board,
    var content: String,
)

/*
fun BoardCommentsRequestDto.toEntity(): BoardComments {
    return BoardComments(
        idx = this.board.idx,
        user = this.user,
        board = this.board,
        content = this.content,
    )
}*/
