package com.nexon.board.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "board_like")
class BoardLike(
    @Column(name = "board_like_idx")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var blIdx: Int? = null,

    @ManyToOne @JoinColumn(name = "b_idx")
    var board: Board,

    @ManyToOne @JoinColumn(name = "u_idx")
    var user: User,

    @Column(name = "b_like_created_date")
    var createdDate: LocalDateTime? = LocalDateTime.now()
)