package com.nexon.board.service

import com.nexon.board.controller.exception.CommonException
import com.nexon.board.controller.exception.CommonExceptionCode
import com.nexon.board.domain.Board
import com.nexon.board.domain.BoardComments
import com.nexon.board.domain.BoardSubComments
import com.nexon.board.domain.User
import com.nexon.board.dto.BoardSubCommentDto
import com.nexon.board.dto.board.BoardCommentDto
import com.nexon.board.repository.BoardCommentRepository
import com.nexon.board.repository.BoardSubCommentsRepository
import com.nexon.board.repository.CommentLikeRepository
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class CommonMethodService(
    private val boardCommentRepository: BoardCommentRepository,
    private val commentLikeRepository: CommentLikeRepository,
    private val boardSubCommentsRepository: BoardSubCommentsRepository
) {
    fun commentListBuilder(board: Board, user: User):List<BoardCommentDto>{
        return boardCommentRepository.findByBoard(board).map {

            var subCommentList = subCommentListBuilder(it)

            boardCommentDtoBuilder(it,subCommentList,user)
        }
    }

    fun subCommentListBuilder(boardComments: BoardComments): List<BoardSubCommentDto>{

        val subComments = boardSubCommentsRepository.findByBoardComments(boardComments)
            ?: throw CommonException(CommonExceptionCode.NO_SUB_COMMENT)

        return subComments.map { subComment ->
            // 서브 댓글을 DTO로 변환
            subCommentBuilder(subComment)
        }

    }

    fun boardCommentDtoBuilder(boardComments: BoardComments,subCommentList:List<BoardSubCommentDto>,user:User): BoardCommentDto{
        return BoardCommentDto(
                commentIdx = boardComments.idx!!,
                userImg = boardComments.user.profileImg ?: "",
                userIdx = boardComments.user.idx!!,
                userName = boardComments.user.userName,
                userGradeImg = boardComments.user.grade.picture,
                comment = boardComments.content,
                fileUrl = boardComments.commentImg?: "",
                commentDate = boardComments.createdDate!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                heartCount = boardComments.heartCount!!,
                isHeartFilled = commentLikeRepository.findByCommentAndUser(boardComments,user) != null,
                subCommentList = subCommentList
        )
    }

    fun subCommentBuilder(subComment: BoardSubComments): BoardSubCommentDto{
        return BoardSubCommentDto(
            subCommentIdx = subComment.idx!!,
            userImg = subComment.user.profileImg!!,
            userIdx = subComment.user.idx!!,
            userName = subComment.user.userName,
            relativeUserName = subComment.relativeUser.userName,
            userGradeImg = subComment.user.grade?.picture!!,
            comment = subComment.content,
            fileUrl = subComment.subCommentImg?: "",
            commentDate = subComment.createdDate!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )
    }

}