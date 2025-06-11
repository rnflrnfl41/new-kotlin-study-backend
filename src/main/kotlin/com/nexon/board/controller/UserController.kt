package com.nexon.board.controller

import com.nexon.board.domain.SignInRequest
import com.nexon.board.domain.User
import com.nexon.board.dto.InsertUserDto
import com.nexon.board.dto.SignInResponse
import com.nexon.board.dto.findUserIdRequestDto
import com.nexon.board.dto.findUserIdResponseDto
import com.nexon.board.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.*
import software.amazon.awssdk.core.sync.RequestBody as awsRequestBody

@RestController
@RequestMapping("user")
class UserController(
    private val userService: UserService,
    ) {


    @GetMapping("/getAll")
    fun findAllUser() : MutableIterable<User> {
        return userService.findAllUser();
    }

    @GetMapping("/findByUserId")
    fun findUserById(@RequestParam userId:String) : Boolean {
        return userService.isDuplicatedId(userId)
    }

    @PostMapping("/findUserId")
    fun findUserId(@RequestBody req: findUserIdRequestDto) : findUserIdResponseDto {
        return userService.findUserId(req)
    }

    @PostMapping("/isUserExist")
    fun isUserExist(@RequestBody req: findUserIdRequestDto) : Boolean {
        return userService.isUserExist(req)
    }

    @PostMapping("/insert")
    fun insertUser(@RequestBody userDto: InsertUserDto) : ResponseEntity<Any> {
        userService.insertUser(userDto)
        return ResponseEntity("저장되었습니다.", HttpStatus.OK)
    }

    @PostMapping("/setNewPassword")
    fun setNewPassword(@RequestBody req:findUserIdRequestDto):Boolean {
        return userService.setNewPassword(req)
    }

    @PostMapping("/uploadProfileImg")
    fun uploadProfileImg(@RequestParam file: MultipartFile):String {
        return userService.uploadProfileImg(file)
    }


}

