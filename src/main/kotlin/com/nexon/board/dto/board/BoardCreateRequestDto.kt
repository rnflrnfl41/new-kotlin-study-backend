package com.nexon.board.dto.board


data class BoardCreateRequestDto (
    var userIdx: Int,
    var category: String,
    var title: String,
    var content: String,
    var likeCount: Int?,
    var views: Int?,
    var isNotice: Boolean?
)