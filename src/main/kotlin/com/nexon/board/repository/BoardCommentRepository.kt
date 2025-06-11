package com.nexon.board.repository

import com.nexon.board.domain.Board
import com.nexon.board.domain.BoardComments
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardCommentRepository: JpaRepository<BoardComments, Int> {

    fun findByIdx(idx:Int):BoardComments?

    fun findByBoard(board: Board):List<BoardComments>
}