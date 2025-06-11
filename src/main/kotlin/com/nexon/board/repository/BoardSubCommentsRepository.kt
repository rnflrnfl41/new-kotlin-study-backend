package com.nexon.board.repository

import com.nexon.board.domain.BoardComments
import com.nexon.board.domain.BoardSubComments
import com.nexon.board.dto.BoardSubCommentDto
import org.springframework.data.jpa.repository.JpaRepository

interface BoardSubCommentsRepository:JpaRepository<BoardSubComments, Int> {

    fun findByIdx(idx:Int):BoardSubComments?
    fun findByBoardComments(boardComments: BoardComments):List<BoardSubComments>?
}