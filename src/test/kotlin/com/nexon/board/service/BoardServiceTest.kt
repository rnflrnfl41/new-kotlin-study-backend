package com.nexon.board.service

import com.nexon.board.domain.Board
import com.nexon.board.domain.User
import com.nexon.board.domain.UserGrade
import com.nexon.board.dto.board.BoardCreateRequestDto
import com.nexon.board.repository.BoardRepository
import com.nexon.board.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest
class BoardServiceTest {

    @MockBean
    private lateinit var boardRepository: BoardRepository

    @MockBean
    private lateinit var userRepository: UserRepository

    private lateinit var boardService: BoardService

    /*@BeforeEach
    fun setup() {
        boardService = BoardService(boardRepository, userRepository)
    }

    @Test
    fun testCreateBoard() {
        // Given
        val userGrade = UserGrade(
            idx = 1,
            name = "Gold",
            picture = "gold.png",
            benefit = "Exclusive benefits for gold members"
        )

        val user = User(
            idx = 1,
            grade = userGrade,
            userId = "testUser",
            userName = "Test User",
            email = "test@example.com",
            phoneNumber = "123456789",
            password = "password",
            address1 = "123 Street",
            address2 = "Apt 1",
            sex = "Male",
            birth = "1990-01-01",
            intro = "Test intro",
            profilePicture = "profile.jpg",
            profileImg = "img.jpg"
        )

        val boardRequest = BoardCreateRequestDto(
            userIdx = user.idx!!,
            title = "Test Title",
            content = "Test Content",
            likeCount = 0,
            views = 0,
            category = "Test Category",
            isNotice = false
        )

        `when`(userRepository.findById(user.idx!!)).thenReturn(Optional.of(user))
        `when`(boardRepository.save(any(Board::class.java))).thenAnswer { it.arguments[0] }

        // When
        boardService.createBoard(boardRequest)

        // Then
        verify(userRepository).findById(user.idx!!)
        verify(boardRepository).save(any(Board::class.java))
    }*/
}
