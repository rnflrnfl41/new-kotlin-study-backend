package com.nexon.board.service

import com.nexon.board.domain.Board
import com.nexon.board.domain.BoardComments
import com.nexon.board.domain.User
import com.nexon.board.domain.UserGrade
import com.nexon.board.repository.BoardCommentRepository
import com.nexon.board.repository.BoardRepository
import com.nexon.board.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
class BoardCommentServiceTest {
    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var boardRepository: BoardRepository

    @MockBean
    private lateinit var boardCommentRepository: BoardCommentRepository


    private lateinit var boardCommentService: BoardCommentService

    /* @BeforeEach
     fun setup() {
         boardCommentService = BoardCommentService(userRepository, boardRepository, boardCommentRepository)
     }

     @Test
     fun testCreateCommentsService() {
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

         val board = Board(
             idx = 1,
             user = user,
             title = "Test Title",
             content = "Test Content",
             likeCount = 0,
             views = 0,
             category = "Test Category",
             createdDate = LocalDateTime.now(),
             updatedDate = LocalDateTime.now(),
             isNotice = false
         )


         // when
         `when`(userRepository.findById(user.idx!!)).thenReturn(Optional.of(user))

         board.idx?.let { idx ->
             `when`(boardRepository.findById(idx)).thenReturn(Optional.of(board))
         }

         `when`(boardCommentRepository.save(any(BoardComments::class.java))).thenAnswer { it.arguments[0] }

         boardCommentService.createComment(dto)


         // Then
         verify(userRepository).findById(user.idx!!)
         verify(boardRepository).findById(board.idx!!)
         verify(boardCommentRepository).save(any(BoardComments::class.java))

     }*/
}