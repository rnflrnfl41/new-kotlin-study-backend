package com.nexon.board.domain

import jakarta.persistence.*
import lombok.ToString
import java.time.LocalDateTime

@Entity
@Table(name = "board")
@ToString
class Board (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "b_idx")
    var idx: Int? = null,

    @ManyToOne
    @JoinColumn(name = "u_idx")
    var user: User,

    @Column(name = "b_title")
    var title: String,

    @Column(name = "b_content")
    var content: String,

    @Column(name = "b_like_count")
    var likeCount: Int? = 0,

    @Column(name = "b_views")
    var views: Int? = 0,

    @ManyToOne
    @JoinColumn(name = "category_idx")
    var category: BoardCategory,

    @Column(name = "b_created_date")
    var createdDate: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "b_updated_date")
    var updatedDate: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "is_notice")
    var isNotice: Boolean? = false,
)
