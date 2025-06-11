package com.nexon.board.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "board_comments")
class BoardComments (
    @Column(name = "b_comments_idx")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Int? = null,

    @ManyToOne @JoinColumn(name = "u_idx")
    var user: User,

    @ManyToOne @JoinColumn(name = "b_idx")
    var board: Board,

    @Column(name = "b_comments_content")
    var content: String,

    @Column(name = "b_comments_heart_count")
    var heartCount: Int? = 0,

    @Column(name = "b_comments_img")
    var commentImg: String?,

    @Column(name = "b_comments_created_date")
    var createdDate: LocalDateTime? = LocalDateTime.now(),
)