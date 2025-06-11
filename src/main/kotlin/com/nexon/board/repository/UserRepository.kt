package com.nexon.board.repository

import com.nexon.board.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository:JpaRepository<User,Int> {

    fun findByIdx(idx:Int):User?
    fun findByUserId(userId:String):User?

    fun findByUserNameAndPhoneNumber(userName:String?, phoneNumber:String?):Optional<User>

    fun findByUserIdAndUserNameAndPhoneNumber(userId:String?,userName:String?,phoneNumber:String?):Optional<User>

}