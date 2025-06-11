package com.nexon.board.repository

import com.nexon.board.domain.UserGrade
import org.springframework.data.jpa.repository.JpaRepository

interface UserGradeRepository: JpaRepository<UserGrade, Int> {
}