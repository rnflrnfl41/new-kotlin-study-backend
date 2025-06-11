package com.nexon.board.repository

import com.nexon.board.domain.Board
import com.nexon.board.domain.BoardCategory
import jdk.jfr.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BoardRepository: JpaRepository<Board, Int> {

    fun findByIdx(idx: Int):Board?
    fun findByCategoryOrderByCreatedDateDesc(category: BoardCategory):List<Board>?

}