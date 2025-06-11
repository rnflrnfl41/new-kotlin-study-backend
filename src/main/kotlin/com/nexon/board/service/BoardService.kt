package com.nexon.board.service

import CustomUserDetails
import com.nexon.board.controller.exception.CommonException
import com.nexon.board.controller.exception.CommonExceptionCode
import com.nexon.board.domain.*
import com.nexon.board.dto.board.*
import com.nexon.board.dto.boardComment.CommentLikeSaveRequest
import com.nexon.board.repository.*
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository,
    private val boardCategoryRepository: BoardCategoryRepository,
    private val boardCommentRepository: BoardCommentRepository,
    private val boardLikeRepository: BoardLikeRepository,
    private val commonMethodService: CommonMethodService
) {
    @Transactional
    fun createBoard(request: BoardCreateRequestDto): Int? {
        val user = userRepository.findById(request.userIdx).orElseThrow { CommonException(CommonExceptionCode.NO_USER) }

        val board = Board(
            user = user,
            title = request.title,
            content = request.content,
            category = boardCategoryRepository.findByCategoryName(request.category)
                ?: throw CommonException(CommonExceptionCode.NO_CATEGORY),
            isNotice = request.isNotice
        )

        boardRepository.save(board)

        return user.idx
    }


    @Transactional
    fun updateBoard(boardId: Int, request: BoardUpdateRequestDto): Int? {
        val findBoard: Board? = boardRepository.findById(boardId).orElse(null)

        findBoard?.let { board ->
            val authorId = findBoard.user.userId
            val user = userRepository.findById(request.userIdx).orElseThrow()

            if (authorId == user.userId) {

                board.apply {
                    title = request.updatedTitle
                    content = request.updatedContent
                    updatedDate = LocalDateTime.now()
                    likeCount = board.likeCount
                    views = board.views
                    category = boardCategoryRepository.findByCategoryName(request.updatedCategory)?:
                    throw CommonException(CommonExceptionCode.NO_CATEGORY)
                    isNotice = request.updatedNotice
                }

                boardRepository.save(board)
            }
        }

        return findBoard?.user!!.idx
    }


    @Transactional
    fun deleteBoard(boardId: Int, userIdx: Int): Int {
        val findBoard: Board? = boardRepository.findById(boardId).orElseThrow()
        val authorId = findBoard?.user?.idx

        if (authorId == userIdx) {
            findBoard.idx?.let { boardRepository.deleteById(it) }
        } else {
            throw IllegalArgumentException("해당 유저는 권한이 없습니다.")
        }

        return authorId
    }


    fun getBoardById(boardId: Int, userIdx: Int): BoardDetailDto? {
        val board = boardRepository.findByIdx(boardId) ?: return null
        var user = userRepository.findByIdx(userIdx)!!

        if(board.user.idx != userIdx){
            board.views = board.views!!.plus(1)
            boardRepository.save(board)
        }

        return BoardDetailDto(
            userIdx = board.user.idx,
            userName = board.user.userName,
            userProfile = board.user.profileImg,
            userGradeProfile = board.user.grade.picture,
            postDate = board.createdDate!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            likeCount = board.likeCount,
            views = board.views,
            boardIdx = board.idx!!,
            boardContent = board.content,
            category = board.category,
            boardTitle = board.title,
            commentList = commonMethodService.commentListBuilder(board,user)
        )
    }


    fun getCategoryList(): List<BoardCategory>? {
        return boardCategoryRepository.findAll()
    }

    fun getBoardList(categoryIdx: Int): BoardListDto? {

        val category = boardCategoryRepository.findByIdx(categoryIdx)
            ?: throw CommonException(CommonExceptionCode.NO_CATEGORY)

        return BoardListDto(
            boardRepository.findByCategoryOrderByCreatedDateDesc(category)?:
            throw CommonException(CommonExceptionCode.NO_BOARD),
            category.categoryName
        )

    }

    @Transactional
    fun saveBoardLike(req: BoardLikeSaveRequest, userDetails: CustomUserDetails) {

        changeBoardHearCount(req.bIdx,req.likeCount)

        boardLikeRepository.save(
            BoardLike(
                board = boardRepository.findByIdx(req.bIdx)!!,
                user = userRepository.findByIdx(userDetails.userIdx)!!
            )
        )
    }

    @Transactional
    fun deleteBoardLike(req: BoardLikeSaveRequest, userDetails: CustomUserDetails) {

        changeBoardHearCount(req.bIdx,req.likeCount)

        boardLikeRepository.deleteByBoardAndUser(
            boardRepository.findByIdx(req.bIdx)!!,
            userRepository.findByIdx(userDetails.userIdx)!!
        )
    }

    fun changeBoardHearCount(bIdx:Int, likeCount:Int) {
        boardRepository.findByIdx(bIdx)?.let { board ->
            board.likeCount = likeCount
            boardRepository.save(board)
        } ?: throw CommonException(CommonExceptionCode.NO_BOARD)
    }


}
