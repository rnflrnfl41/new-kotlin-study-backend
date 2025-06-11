package com.nexon.board.service

import com.nexon.board.config.TokenProvider
import com.nexon.board.controller.exception.CommonException
import com.nexon.board.controller.exception.CommonExceptionCode
import com.nexon.board.domain.SignInRequest
import com.nexon.board.domain.User
import com.nexon.board.dto.SignInResponse
import com.nexon.board.repository.UserRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class SignService(
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder,
    private val tokenProvider: TokenProvider
) {

    /* @Transactional
    fun registMember(request: SignUpRequest) = SignUpResponse.from(
        userRepository.flushOrThrow(IllegalArgumentException("이미 사용중인 아이디입니다.")) {
            save(User.from(request, encoder))	// 회원가입 정보를 암호화하도록 수정
        }
    )*/

    /*@Transactional
    fun signIn(request: SignInRequest): SignInResponse {
        val member = userRepository.findByUserId(request.account)
            ?.takeIf { encoder.matches(request.password, it.get().password) }
            ?: throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")
        val token = tokenProvider.createToken("${member.get().userId}")	// 토큰 생성
        return SignInResponse(member.get().userName, token)	// 생성자에 토큰 추가
    }*/

    @Transactional
    fun signIn(request: SignInRequest): SignInResponse {

        var user: User = userRepository.findByUserId(request.account)
            ?: throw CommonException(CommonExceptionCode.ID_PASSWORD_FAIL)

        if(!encoder.matches(request.password,user.password)){
            throw CommonException(CommonExceptionCode.ID_PASSWORD_FAIL)
        }

        val token = tokenProvider.createToken("${user.userId}")
        return SignInResponse(user.idx,user.userName,user.userId,token,user.profileImg?:"")

    }
}