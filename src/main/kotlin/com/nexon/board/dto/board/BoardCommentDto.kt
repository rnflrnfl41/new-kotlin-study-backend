package com.nexon.board.dto.board

import com.nexon.board.domain.BoardSubComments
import com.nexon.board.dto.BoardSubCommentDto

data class BoardCommentDto(
    var commentIdx: Int,
    var userImg : String,
    var userIdx: Int,
    var userName : String,
    var userGradeImg : String,
    var comment : String,
    var fileUrl : String,
    var commentDate : String,
    var heartCount : Int,
    var isHeartFilled : Boolean?,
    var subCommentList: List<BoardSubCommentDto>

)
