package com.book.library.book.api;

import com.book.library.book.domain.Book;
import com.book.library.book.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    @DisplayName(value = "도서 저장")
    @Test
    void t1() throws Exception {
        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .description("설명")
                .name("모던자바인액션")
                .author("1")
                .build();

        String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookRequestDto);

        mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("모던자바인액션"));
    }

    @Test
    @Transactional
    @DisplayName("도서 id와 BookRequestDto값 유효하면 도서 수정 성공")
    void t2() throws Exception {
        saveBook();
        Long bookId = 1L;
        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .description("쉽게 자바를 공부해보자")
                .name("자바의 신2")
                .author("이상민")
                .build();

        String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookRequestDto);

        mockMvc.perform(put("/api/books/{id}",bookId).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("자바의 신2"))
                .andExpect(jsonPath("$.author").value("이상민"))
                .andExpect(jsonPath("$.description").value("쉽게 자바를 공부해보자"))
        ;
    }

    @Test
    @Transactional
    @DisplayName("도서 id, 회원 id 유효하면 도서 대출 성공")
    void t3() throws Exception {
        saveBook();
        saveMember();

        Long bookId = 1L;
        Long memberId = 1L;

        mockMvc.perform(post("/api/books/{bookId}/rent", bookId).param("memberId",memberId.toString()))
                .andExpect(status().isCreated())
        ;
    }

    private void saveMember() {
        memberRepository.save(Member.builder()
                .memberId("sdp")
                .password("1234")
                .name("dongpil")
                .age(30)
                .email("pildong@naver.com")
                .phoneNumber(010)
                .build());
    }

    private void saveBook() {
        Book book = Book.builder()
                .name("자바의 신")
                .description("자바 책")
                .author("")
                .build();

        bookRepository.save(book);
    }
}