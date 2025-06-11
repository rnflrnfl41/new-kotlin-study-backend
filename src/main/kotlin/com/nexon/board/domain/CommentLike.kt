package com.nexon.board.domain

import jakarta.persistence.*

@Entity
@Table(name = "comment_like")
class CommentLike (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_idx")
    var clIdx: Int? = null,

    @ManyToOne @JoinColumn(name = "c_idx")
    var comment: BoardComments,

    @ManyToOne @JoinColumn(name = "u_idx")
    var user: User
)