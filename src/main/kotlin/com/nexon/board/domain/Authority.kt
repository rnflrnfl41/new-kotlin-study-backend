package com.nexon.board.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "authority")
class Authority (
    @Column(name = "authority_idx")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var idx: Int,
)