package com.nexon.board.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "board_file")
class BoardFile (
    @Column(name = "b_file_idx")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var idx: Int,

    @Column(name = "b_file_url")
    var url: String,

    @Column(name = "b_file_created_date")
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "b_file_updated_date")
    var updatedDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "b_file_size")
    var size: String,

    @Column(name = "b_file_name")
    var name: String,

    @Column(name = "b_file_type")
    var type: String,
)