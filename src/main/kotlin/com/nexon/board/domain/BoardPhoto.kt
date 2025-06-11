package com.nexon.board.domain

import jakarta.persistence.*

@Entity
@Table(name = "board_photo")
class BoardPhoto (
    @Column(name = "b_photo_idx")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var idx: Int,

    @ManyToOne @JoinColumn(name = "b_idx", insertable = false, updatable = false)
    var board: Board,

    @ManyToOne @JoinColumn(name = "b_file_idx", insertable = false, updatable = false)
    var boardFile: BoardFile
)