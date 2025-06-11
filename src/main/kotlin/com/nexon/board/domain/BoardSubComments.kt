package com.nexon.board.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "board_sub_comments")
class BoardSubComments (
    @Column(name = "b_sub_comments_idx")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Int? = null,

    @ManyToOne
    @JoinColumn(name = "u_idx")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "b_sub_relative_u_idx")
    var relativeUser: User,

    @ManyToOne
    @JoinColumn(name = "b_comments_idx")
    var boardComments: BoardComments,

    @Column(name = "b_sub_comments_img")
    var subCommentImg: String?,

    @Column(name = "b_sub_comments_content")
    var content: String,

    @Column(name = "b_sub_comments_created_date")
    var createdDate: LocalDateTime? = LocalDateTime.now(),
)