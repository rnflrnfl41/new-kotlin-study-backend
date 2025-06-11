package com.nexon.board.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name ="user_grade")
class UserGrade(
    @Column(name = "g_idx")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var idx: Int,

    @Column(name = "g_name")
    var name: String,

    @Column(name = "g_picture")
    var picture: String,

    @Column(name = "g_benefit")
    var benefit: String
)