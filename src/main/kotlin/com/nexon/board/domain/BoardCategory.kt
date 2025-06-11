package com.nexon.board.domain

import jakarta.persistence.*
import lombok.Getter
import lombok.Setter

@Entity
@Table(name = "board_category")
class BoardCategory (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_idx")
    var idx: Int? = null,

    @Column(name = "category_name")
    var categoryName: String,
)