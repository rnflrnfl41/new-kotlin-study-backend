package com.nexon.board.repository

import com.nexon.board.domain.BoardComments
import com.nexon.board.domain.CommentLike
import com.nexon.board.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface CommentLikeRepository: JpaRepository<CommentLike, Int> {

    fun findByCommentAndUser(comment: BoardComments, user: User): CommentLike?

    fun deleteByCommentAndUser(comment: BoardComments, user: User)
}