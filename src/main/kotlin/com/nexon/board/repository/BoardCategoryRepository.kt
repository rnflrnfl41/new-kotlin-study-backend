package com.nexon.board.repository

import com.nexon.board.domain.BoardCategory
import com.nexon.board.domain.BoardComments
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardCategoryRepository: JpaRepository<BoardCategory, Int> {

    @Query("select c.categoryName from BoardCategory c where c.idx = :idx")
    fun getCategoryNameByIdx(idx:Int):String?

    fun findByIdx(idx:Int):BoardCategory?

    fun findByCategoryName(categoryName:String):BoardCategory?

}