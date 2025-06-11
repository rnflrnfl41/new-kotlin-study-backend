package com.nexon.board.service

import CustomUserDetails
import com.nexon.board.controller.exception.CommonException
import com.nexon.board.controller.exception.CommonExceptionCode
import com.nexon.board.domain.BoardComments
import com.nexon.board.domain.BoardSubComments
import com.nexon.board.domain.CommentLike
import com.nexon.board.dto.BoardSubCommentDto
import com.nexon.board.dto.board.BoardCommentDto
import com.nexon.board.dto.board.BoardCommentSaveRequest
import com.nexon.board.dto.boardComment.*
import com.nexon.board.repository.*
import jakarta.transaction.Transactional
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class BoardCommentService(
    private val userRepository: UserRepository,
    private val boardRepository: BoardRepository,
    private val boardCommentRepository: BoardCommentRepository,
    private val commentLikeRepository: CommentLikeRepository,
    private val commonMethodService: CommonMethodService,
    private val boardSubCommentsRepository: BoardSubCommentsRepository,
    private val awsFileService: AwsFileService
) {

    @Transactional
    fun saveComment(request: BoardCommentSaveRequest, userDetails: CustomUserDetails): List<BoardCommentDto>? {
        var board =
            boardRepository.findByIdx(request.boardIdx)
                ?: throw CommonException(CommonExceptionCode.NO_BOARD)

        var user = userRepository.findByIdx(userDetails.userIdx)!!

        boardCommentRepository.save(
            BoardComments(
                user = userRepository.findByIdx(request.userIdx)?: throw CommonException(CommonExceptionCode.NO_USER),
                board = board,
                content = request.comment,
                commentImg = request.fileUrl
            )
        )


        return commonMethodService.commentListBuilder(board,user)
    }


    @Transactional
    fun updateComment(commentIdx: Int, request: BoardCommentsUpdateRequestDto, userDetails: CustomUserDetails): BoardCommentDto {
        val findComment =
            boardCommentRepository.findByIdx(commentIdx)
                ?: throw CommonException(CommonExceptionCode.NO_COMMENT)

        //권한 체크
        if (findComment.user.idx != userDetails.userIdx) {
            throw CommonException(CommonExceptionCode.NO_AUTH)
        }

        //댓글 업데이트
        findComment.content = request.updateComment
        if(findComment.commentImg != request.fileUrl){
            findComment.commentImg?.let { awsFileService.deleteFile(it) }
            findComment.commentImg = request.fileUrl
        }
        boardCommentRepository.save(findComment)

        val user = userRepository.findByIdx(userDetails.userIdx)
            ?: throw CommonException(CommonExceptionCode.NO_USER)

        val subCommentDto = commonMethodService.subCommentListBuilder(findComment)

        return commonMethodService.boardCommentDtoBuilder(findComment,subCommentDto,user)
    }


    @Transactional
    fun deleteComment(request: BoardCommentDeleteRequest,
                      userDetails: CustomUserDetails): List<BoardCommentDto> {

        // 게시글 정보 조회
        val board = boardRepository.findByIdx(request.boardIdx)
                ?: throw CommonException(CommonExceptionCode.NO_BOARD)

        // 사용자 정보 조회
        val user = userRepository.findByIdx(userDetails.userIdx)
                ?: throw CommonException(CommonExceptionCode.NO_USER)

        // 댓글 조회
        val comment = boardCommentRepository.findByIdx(request.commentIdx)
                ?: throw CommonException(CommonExceptionCode.NO_COMMENT)

        //댓글 삭제
        boardCommentRepository.delete(comment)

        // 댓글 이미지가 있을 경우 삭제
        comment.commentImg?.let { awsFileService.deleteFile(it) }


        // 댓글 목록 반환
        return commonMethodService.commentListBuilder(board, user)
    }


    @Transactional
    fun saveCommentLike(req: CommentLikeSaveRequest, userDetails: CustomUserDetails) {

        changeCommentHearCount(req.cIdx,req.likeCount)

        commentLikeRepository.save(
            CommentLike(
            comment = boardCommentRepository.findByIdx(req.cIdx)!!,
            user = userRepository.findByIdx(userDetails.userIdx)!!
        )
        )
    }

    @Transactional
    fun deleteCommentLike(req: CommentLikeSaveRequest, userDetails: CustomUserDetails) {

        changeCommentHearCount(req.cIdx,req.likeCount)

        commentLikeRepository.deleteByCommentAndUser(
            boardCommentRepository.findByIdx(req.cIdx)!!,
            userRepository.findByIdx(userDetails.userIdx)!!
        )
    }

    @Transactional
    fun changeCommentHearCount(cIdx:Int, likeCount:Int) {
        boardCommentRepository.findByIdx(cIdx)?.let { comment ->
            comment.heartCount = likeCount
            boardCommentRepository.save(comment)
        } ?: throw CommonException(CommonExceptionCode.NO_COMMENT)
    }

    @Transactional
    fun saveSubComment(request: BoardSubCommentSaveRequest,userDetails: CustomUserDetails): BoardCommentDto{

        var user = userRepository.findByIdx(userDetails.userIdx)!!

        boardSubCommentsRepository.save(BoardSubComments(
                user = userRepository.findByIdx(userDetails.userIdx)?: throw CommonException(CommonExceptionCode.NO_USER),
                content = request.subComment,
                relativeUser = userRepository.findByIdx(request.relativeUserIdx)?: throw CommonException(CommonExceptionCode.NO_USER),
                boardComments = boardCommentRepository.findByIdx(request.commentIdx)?: throw CommonException(CommonExceptionCode.NO_COMMENT),
                subCommentImg = request.fileUrl
        ))

        return boardCommentRepository.findByIdx(request.commentIdx)?.let {
            var subCommentList = commonMethodService.subCommentListBuilder(it)
            commonMethodService.boardCommentDtoBuilder(it,subCommentList,user)
        }?: throw CommonException(CommonExceptionCode.NO_COMMENT)

    }

    @Transactional
    fun deleteSubComment(request: SubCommentDeleteRequest, userDetails: CustomUserDetails): BoardCommentDto {
        var user = userRepository.findByIdx(userDetails.userIdx)!!

        // 대댓글 조회
        val comment = boardSubCommentsRepository.findByIdx(request.subCommentIdx)
                ?: throw CommonException(CommonExceptionCode.NO_COMMENT)

        //대댓글 삭제
        boardSubCommentsRepository.delete(comment)

        // 대댓글 이미지가 있을 경우 삭제
        comment.subCommentImg?.let { awsFileService.deleteFile(it) }

        //삭제된 대댓글 빼고 댓글 다시 리로드
        return boardCommentRepository.findByIdx(request.commentIdx)?.let {
            var subCommentList = commonMethodService.subCommentListBuilder(it)
            commonMethodService.boardCommentDtoBuilder(it,subCommentList,user)
        }?: throw CommonException(CommonExceptionCode.NO_COMMENT)
    }

    fun updateSubComment(commentId: Int, request: BoardCommentsUpdateRequestDto, userDetails: CustomUserDetails): BoardSubCommentDto {

        val findSubComment =
            boardSubCommentsRepository.findByIdx(commentId)
                ?: throw CommonException(CommonExceptionCode.NO_COMMENT)

        //권한 체크
        if (findSubComment.user.idx != userDetails.userIdx) {
            throw CommonException(CommonExceptionCode.NO_AUTH)
        }

        //댓글 업데이트
        findSubComment.content = request.updateComment
        if(findSubComment.subCommentImg != request.fileUrl){
            findSubComment.subCommentImg?.let { awsFileService.deleteFile(it) }
            findSubComment.subCommentImg = request.fileUrl
        }

        boardSubCommentsRepository.save(findSubComment)

        return commonMethodService.subCommentBuilder(findSubComment)

    }


}