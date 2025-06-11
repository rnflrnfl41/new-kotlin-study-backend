package com.nexon.board.dto

data class BoardSubCommentDto(
    var subCommentIdx: Int,
    var userIdx : Int,
    var userImg : String,
    var userName : String,
    var relativeUserName: String,
    var userGradeImg : String,
    var comment : String,
    var commentDate : String,
    var fileUrl : String,
)
