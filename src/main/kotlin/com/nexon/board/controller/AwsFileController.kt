package com.nexon.board.controller

import com.nexon.board.controller.exception.CommonException
import com.nexon.board.controller.exception.CommonExceptionCode
import com.nexon.board.service.AwsFileService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "AWS S3 CRUD", description = "AWS 파일 업로드, 다운로드, 삭제 지원.")
@RestController
@RequestMapping("file/aws")
class AwsFileController(
        private val awsFileService: AwsFileService
) {

    @Operation(
            operationId = "uploadFile",
            summary = "파일 업로드",
            description = "파일 업로드 api",
            requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "댓글 생성에 필요한 정보",
                    content = [Content(
                            mediaType = "multipart/form-data",
                            schema = Schema(implementation = MultipartFile::class)
                    )]
            ),
            responses = [
                ApiResponse(responseCode = "200", description = "파일 URL"),
                ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            ],
            tags = ["파일 업로드 api"]
    )
    @PostMapping("/uploadFile")
    fun uploadFile(@RequestParam file: MultipartFile,
                   @RequestParam fileName:String):ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(awsFileService.uploadProfileImg(file,fileName))
    }

    @PostMapping("deleteFile")
    fun deleteFile(@RequestBody request:Map<String, String>): ResponseEntity<String> {

        val filePath = request["filePath"]

        if (filePath == null || filePath.isEmpty()) {
            throw CommonException(CommonExceptionCode.NO_FILEPATH)
        }

        return if (awsFileService.deleteFile(filePath)) {
            ResponseEntity.ok("이미지 삭제 성공")
        } else {
            ResponseEntity.status(500).body("이미지 삭제 실패")
        }
    }

}