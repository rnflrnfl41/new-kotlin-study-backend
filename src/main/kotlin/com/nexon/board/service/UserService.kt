package com.nexon.board.service

import CustomUserDetails
import com.nexon.board.config.TokenProvider
import com.nexon.board.controller.exception.CommonException
import com.nexon.board.controller.exception.CommonExceptionCode
import com.nexon.board.domain.User
import com.nexon.board.dto.InsertUserDto
import com.nexon.board.dto.findUserIdRequestDto
import com.nexon.board.dto.findUserIdResponseDto
import com.nexon.board.repository.UserGradeRepository
import com.nexon.board.repository.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.*


@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userGradeRepository: UserGradeRepository

    @Autowired
    lateinit var encoder:PasswordEncoder

    @Autowired
    lateinit var s3Client: S3Client

    @Value("\${s3.bucket}")
    private lateinit var bucketName: String

    @Value("\${s3.region}")
    private lateinit var region: String

    fun insertUser(userParam: InsertUserDto) :User {

        var userGrade = userGradeRepository.findById(0).get()

        userParam.password = encoder.encode(userParam.password)
        var user = User(
            userId = userParam.userId,
            userName = userParam.userName,
            email = userParam.email,
            phoneNumber = userParam.phoneNumber,
            password = userParam.password,
            address1 = userParam.address1,
            address2 = userParam.address2,
            gender = userParam.gender,
            birth = userParam.birth,
            grade = userGrade,
            )

        try {
            userRepository.save(user)
        } catch (e: Exception) {
            throw CommonException(CommonExceptionCode.SAVE_FAILED)
        }

        return user

    }

    fun findAllUser(): MutableIterable<User> {
        return userRepository.findAll();
    }

    fun findByUserById(id:Int): User?{
        return userRepository.findByIdOrNull(id)
    }

    fun isDuplicatedId(id:String): Boolean {
        if(userRepository.findByUserId(id) != null){
            throw CommonException(CommonExceptionCode.DUPLICATE_ID)
        }else{
            return true
        }
    }

    fun setUserCustomDetail(id:String): CustomUserDetails {
        var user = userRepository.findByUserId(id)?:throw CommonException(CommonExceptionCode.NO_USER_BY_ID)

        return CustomUserDetails(
            userName = user.userId,
            password = user.password,
            authorities = listOf(SimpleGrantedAuthority("ROLE_USER")),
            userIdx = user.idx!!
        )
    }

    fun findByUserId(id:String): User {
        return userRepository.findByUserId(id)?:throw CommonException(CommonExceptionCode.NO_USER_BY_ID)
    }

    fun findUserId(req: findUserIdRequestDto): findUserIdResponseDto {
        val user = userRepository.findByUserNameAndPhoneNumber(req.userName,req.userPhone)
            .orElseThrow{ CommonException(CommonExceptionCode.NO_USER_BY_ID)}
        val message = "해당 유저의 ID는 "+user.userId+ " 입니다.";
        return findUserIdResponseDto(user.userId,message)
    }

    fun isUserExist(req: findUserIdRequestDto): Boolean {
        userRepository.findByUserIdAndUserNameAndPhoneNumber(req.userId,req.userName,req.userPhone)
            .orElseThrow({CommonException(CommonExceptionCode.NO_USER_BY_ID)})

        return true
    }

    fun setNewPassword(req: findUserIdRequestDto):Boolean {
        userRepository.findByUserIdAndUserNameAndPhoneNumber(req.userId,req.userName,req.userPhone)
            .ifPresent  { u ->
                u.password = encoder.encode(req.newPassword)
                userRepository.save(u)
            }

        return true
    }

    fun uploadProfileImg(multipartFile: MultipartFile): String {
        val uniqueFileName = UUID.randomUUID().toString() + "-" + multipartFile.originalFilename

        // S3에 업로드
        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uniqueFileName)
                .contentType(multipartFile.contentType)
                .build(),
            RequestBody.fromBytes(multipartFile.bytes)
        )

        // 업로드된 파일의 URL 반환
        return "https://${bucketName}.s3.${region}.amazonaws.com/${uniqueFileName}"
    }


}