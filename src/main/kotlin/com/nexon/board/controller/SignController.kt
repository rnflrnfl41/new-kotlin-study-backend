package com.nexon.board.controller

import com.nexon.board.domain.SignInRequest
import com.nexon.board.dto.SignInResponse
import com.nexon.board.service.SignService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/signIn")
class SignController(private val signService: SignService) {

    @PostMapping("/process")
    fun login(@RequestBody signRequest: SignInRequest): SignInResponse {
        return signService.signIn(signRequest)
    }
}