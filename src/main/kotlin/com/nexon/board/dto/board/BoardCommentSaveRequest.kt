package com.nexon.board.dto.board

data class BoardCommentSaveRequest(

    var boardIdx: Int,
    var userIdx: Int,
    var comment: String,
    var fileUrl : String?

)
