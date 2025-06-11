    package com.nexon.board.controller

    import CustomUserDetails
    import com.nexon.board.domain.BoardCategory
    import com.nexon.board.dto.board.*
    import com.nexon.board.dto.boardComment.CommentLikeSaveRequest
    import com.nexon.board.service.BoardService
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


    @Tag(name = "게시판", description = "게시판 생성, 업데이트, 조회, 삭제를 포함합니다.")
    @RestController
    @RequestMapping("/board")
    class BoardController(private val boardService: BoardService) {
        @Operation(
            operationId = "board",
            summary = "게시판 글 작성",
            description = "유저 계정의 id, 제목, 내용, 좋아요, 조회수, 카테고리, 공지사항을 포함합니다.",
            requestBody = SwaggerRequestBody(
                description = "게시판 생성에 필요한 정보",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BoardCreateRequestDto::class),
                )]
            ),
            responses = [
                ApiResponse(responseCode = "201", description = "게시글을 정상적으로 생성했습니다."),
                ApiResponse(responseCode = "404", description = "유저 찾을 수 없습니다.")
            ],
            tags = ["board"],
        )
        @PostMapping("/insert")
        fun createBoard(@RequestBody request: BoardCreateRequestDto): ResponseEntity<String> {
            boardService.createBoard(request)
            return ResponseEntity.status(HttpStatus.CREATED).body("게시글을 정상적으로 생성했습니다.")
        }

        @Operation(
            operationId = "getBoardById",
            summary = "게시글 조회",
            description = "게시글 ID로 게시글을 조회합니다.",
            parameters = [
                Parameter(name = "boardId", description = "조회할 게시글 ID", required = true, schema = Schema(type = "integer"))
            ],
            responses = [
                ApiResponse(responseCode = "200", description = "게시글을 정상적으로 조회했습니다."),
                ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다.")
            ],
            tags = ["게시판"]
        )
        @GetMapping("/{boardId}")
        //민진님... return타입 않넣으시면 어쩝니까,,
        fun getBoardById(@PathVariable boardId: Int, @AuthenticationPrincipal userDetails:CustomUserDetails ):ResponseEntity<BoardDetailDto> {
            return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoardById(boardId,userDetails.userIdx))
        }

        @Operation(
            operationId = "updateBoard",
            summary = "게시글 업데이트",
            description = "게시글을 업데이트합니다. 요청 본문에 업데이트할 게시글의 정보가 포함되어야 합니다.",
            parameters = [
                Parameter(name = "boardId", description = "업데이트할 게시글의 ID", required = true, schema = Schema(type = "integer"))
            ],
            requestBody = SwaggerRequestBody(
                description = "게시글 업데이트에 필요한 정보",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BoardUpdateRequestDto::class),
                )]
            ),
            responses = [
                ApiResponse(responseCode = "200", description = "게시글을 정상적으로 업데이트했습니다."),
                ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다."),
                ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            ],
            tags = ["게시판"]
        )
        @PutMapping("/{boardId}")
        fun updateBoard(@PathVariable boardId: Int, @RequestBody request: BoardUpdateRequestDto): ResponseEntity<Int> {
            val userIdx =  boardService.updateBoard(boardId, request)

            return ResponseEntity.status(HttpStatus.OK).body(userIdx)
        }


        @Operation(
            operationId = "deleteBoard",
            summary = "게시글 삭제",
            description = "게시글을 삭제합니다. 게시글 ID와 사용자 ID를 요청 파라미터로 받아야 합니다.",
            parameters = [
                Parameter(name = "boardId", description = "삭제할 게시글의 ID", required = true, schema = Schema(type = "integer")),
                Parameter(name = "userIdx", description = "게시글 삭제를 수행하는 사용자 IDX", required = true, schema = Schema(type = "integer"))
            ],
            responses = [
                ApiResponse(responseCode = "200", description = "게시글을 정상적으로 삭제했습니다."),
                ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다."),
                ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            ],
            tags = ["게시판"]
        )
        @DeleteMapping("/{boardId}")
        fun deleteBoard(@PathVariable boardId: Int, @RequestParam userIdx: Int): ResponseEntity<Int> {
            val chkUserIdx = boardService.deleteBoard(boardId, userIdx)

            return ResponseEntity.status(HttpStatus.OK).body(chkUserIdx)
        }

        @PostMapping("/categoryList")
        fun getCategoryList(): ResponseEntity<List<BoardCategory>>{
            return ResponseEntity.status(HttpStatus.OK).body(boardService.getCategoryList())
        }

        @GetMapping("/boardList/{categoryIdx}")
        fun getBoardList(@PathVariable categoryIdx:Int): ResponseEntity<BoardListDto>{
            return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoardList(categoryIdx))
        }

        @PostMapping("/deleteLike")
        fun deleteLike(@RequestBody request: BoardLikeSaveRequest, @AuthenticationPrincipal userDetails: CustomUserDetails) {
            boardService.deleteBoardLike(request,userDetails)
        }

        @PostMapping("/saveLike")
        fun saveLike(@RequestBody request: BoardLikeSaveRequest, @AuthenticationPrincipal userDetails: CustomUserDetails) {
            boardService.saveBoardLike(request,userDetails)
        }


    }