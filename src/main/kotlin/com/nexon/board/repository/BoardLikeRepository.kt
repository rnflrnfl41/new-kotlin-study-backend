package com.nexon.board.repository

import com.nexon.board.domain.*
import org.springframework.data.jpa.repository.JpaRepository

interface BoardLikeRepository: JpaRepository<BoardLike, Int> {

    fun deleteByBoardAndUser(board: Board, user: User)

}