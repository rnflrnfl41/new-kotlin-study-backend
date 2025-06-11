package com.nexon.board.dto.board

import com.nexon.board.domain.Board
import lombok.Builder

data class BoardListDto(
    var boardList: List<Board>,
    var categoryName: String
)
