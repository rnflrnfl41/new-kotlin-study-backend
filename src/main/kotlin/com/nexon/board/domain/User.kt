package com.nexon.board.domain

import com.nexon.board.dto.InsertUserDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import lombok.Builder
import org.springframework.http.HttpStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

@Entity
@Table(name="user")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_idx")
    var idx: Int? = null,

    @OneToOne @JoinColumn(name = "g_idx")
    var grade: UserGrade,

    @Column(name = "u_id")
    var userId: String,

    @Column(name = "u_name")
    var userName: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "u_phone_number")
    var phoneNumber: String,

    @Column(name = "user_password")
    var password: String,

    @Column(name = "u_address1")
    var address1: String,

    @Column(name = "u_address2")
    var address2: String,

    @Column(name = "u_gender")
    var gender: String,

    @Column(name = "u_birth")
    var birth: String,

    @Column(name = "u_intro")
    var intro: String? = null,

    @Column(name = "u_profile_picture")
    var profilePicture: String? = null,

    @Column(name = "profile_img")
    var profileImg: String? = "https://h-bin-bucket.s3.ap-southeast-2.amazonaws.com/1620b19b-29f7-46d2-88d2-a10d4e620914-user.png",

    @Column(name = "u_recent_access_date")
    var recentAccessDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_date")
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_date")
    var updatedDate: LocalDateTime = LocalDateTime.now(),
)
