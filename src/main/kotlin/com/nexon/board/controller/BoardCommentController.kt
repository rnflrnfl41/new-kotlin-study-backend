package com.nexon.board.controller

import CustomUserDetails
import com.nexon.board.controller.exception.CommonException
import com.nexon.board.controller.exception.CommonExceptionCode
import com.nexon.board.dto.BoardSubCommentDto
import com.nexon.board.dto.board.BoardCommentDto
import com.nexon.board.dto.board.BoardCommentSaveRequest
import com.nexon.board.dto.boardComment.*
import com.nexon.board.service.BoardCommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "게시판 댓글", description = "게시판 댓글 생성, 업데이트, 삭제를 포함합니다.")
@RestController
@RequestMapping("/board/comment")
class BoardCommentController(
    private val boardCommentService: BoardCommentService
) {

    @Operation(
        operationId = "createComment",
        summary = "댓글 생성",
        description = "게시판에 댓글을 생성합니다.",
        requestBody = SwaggerRequestBody(
            description = "댓글 생성에 필요한 정보",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = BoardCommentSaveRequest::class)
            )]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "댓글을 정상적으로 생성했습니다."),
            ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
        ],
        tags = ["게시판 댓글"]
    )
    @PostMapping("/save")
    fun saveComment(@RequestBody request: BoardCommentSaveRequest, @AuthenticationPrincipal userDetails: CustomUserDetails)
    : ResponseEntity<List<BoardCommentDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(boardCommentService.saveComment(request,userDetails))
    }

    @Operation(
        operationId = "updateComment",
        summary = "댓글 업데이트",
        description = "댓글을 업데이트합니다.",
        parameters = [
            Parameter(name = "commentId", description = "업데이트할 댓글의 ID", required = true, schema = Schema(type = "integer"))
        ],
        requestBody = SwaggerRequestBody(
            description = "댓글 업데이트에 필요한 정보",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = BoardCommentsUpdateRequestDto::class)
            )]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "댓글을 정상적으로 업데이트했습니다."),
            ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다."),
            ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
        ],
        tags = ["게시판 댓글"]
    )
    @PatchMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Int,
        @RequestBody updatedComment: BoardCommentsUpdateRequestDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<BoardCommentDto> {
        val boardComment = boardCommentService.updateComment(commentId, updatedComment, userDetails)
        return ResponseEntity.status(HttpStatus.OK).body(boardComment)
    }

    @Operation(
        operationId = "deleteComment",
        summary = "댓글 삭제",
        description = "댓글을 삭제합니다.",
        parameters = [
            Parameter(name = "commentId", description = "삭제할 댓글의 ID", required = true, schema = Schema(type = "integer"))
        ],
        requestBody = SwaggerRequestBody(
            description = "댓글 삭제에 필요한 정보",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = BoardCommentsDeleteRequestDto::class)
            )]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "댓글을 정상적으로 삭제했습니다."),
            ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다."),
            ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
        ],
        tags = ["게시판 댓글"]
    )
    @PostMapping("/delete")
    fun deleteComment(@RequestBody request: BoardCommentDeleteRequest,
                   @AuthenticationPrincipal userDetails: CustomUserDetails): ResponseEntity<List<BoardCommentDto>> {

        return ResponseEntity.ok(boardCommentService.deleteComment(request,userDetails))
    }


    @PostMapping("/deleteLike")
    fun deleteLike(@RequestBody request: CommentLikeSaveRequest, @AuthenticationPrincipal userDetails: CustomUserDetails) {
        boardCommentService.deleteCommentLike(request,userDetails)
    }

    @PostMapping("/saveLike")
    fun saveLike(@RequestBody request: CommentLikeSaveRequest, @AuthenticationPrincipal userDetails: CustomUserDetails) {
        boardCommentService.saveCommentLike(request,userDetails)
    }

    @PostMapping("/saveSubComment")
    fun saveSubComment(@RequestBody request: BoardSubCommentSaveRequest
                       , @AuthenticationPrincipal userDetails: CustomUserDetails)
    : BoardCommentDto{
        return boardCommentService.saveSubComment(request,userDetails)
    }

    @PostMapping("/deleteSub")
    fun deleteSubComment(@RequestBody request: SubCommentDeleteRequest,
                      @AuthenticationPrincipal userDetails: CustomUserDetails): ResponseEntity<BoardCommentDto> {

        return ResponseEntity.ok(boardCommentService.deleteSubComment(request,userDetails))
    }

    @PatchMapping("/sub/{commentId}")
    fun updateSubComment(
        @PathVariable commentId: Int,
        @RequestBody updatedComment: BoardCommentsUpdateRequestDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<BoardSubCommentDto> {
        val boardSubComment = boardCommentService.updateSubComment(commentId, updatedComment, userDetails)
        return ResponseEntity.status(HttpStatus.OK).body(boardSubComment)
    }

}
