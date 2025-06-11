package com.nexon.board.dto.board

import com.nexon.board.domain.BoardCategory

data class BoardDetailDto(
    var userIdx: Int?,
    var userName: String,
    var userProfile: String?,
    var userGradeProfile: String,
    var postDate: String,
    var likeCount: Int?,
    var views: Int?,
    var boardIdx: Int,
    var boardContent: String,
    var category: BoardCategory,
    var boardTitle: String,
    var commentList: List<BoardCommentDto>
)
