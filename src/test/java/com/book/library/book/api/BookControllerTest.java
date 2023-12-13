package com.book.library.book.api;

import com.book.library.book.app.BookService;
import com.book.library.book.domain.Book;
import com.book.library.book.repository.BookRepository;
import com.book.library.member.domain.Member;
import com.book.library.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BookService bookService;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE book ALTER COLUMN id RESTART WITH 1");    }

    @AfterEach
    void deleteAll() {
        bookRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Transactional
    @DisplayName(value = "도서 저장")
    @Test
    void t1() throws Exception {
        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .description("설명")
                .title("모던자바인액션")
                .author("1")
                .build();

        String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookRequestDto);

        mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("모던자바인액션"));
    }

    @Test
    @Transactional
    @DisplayName("도서 id와 BookRequestDto값 유효하면 도서 수정 성공")
    void t2() throws Exception {
        saveBook();
        Long bookId = 1L;
        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .description("쉽게 자바를 공부해보자")
                .title("자바의 신2")
                .author("이상민")
                .build();

        String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookRequestDto);

        mockMvc.perform(put("/api/books/{id}",bookId).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("자바의 신2"))
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
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    @DisplayName("도서 id, 회원 id 유효하면 도서 반납 성공")
    void t4() throws Exception {
        saveBook();
        saveMember();
        bookService.rent(1L, 1L);

        Long bookId = 1L;
        Long memberId = 1L;

        mockMvc.perform(post("/api/books/{bookId}/return", bookId).param("memberId",memberId.toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @DisplayName("회원아이디 유효할 경우 도서 대출 내역 가져오기")
    void t5() throws Exception {
        saveBook();
        saveBook2();
        saveMember();
        bookService.rent(1L, 1L);
        bookService.rent(2L, 1L);

        String memberId = "sdp";

        mockMvc.perform(get("/api/books/{memberId}", memberId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].bookTitle").value("자바의 신"))
                .andExpect(jsonPath("$[0].memberId").value("sdp"))
                .andExpect(jsonPath("$[1].bookTitle").value("자바의 신2"))
                .andExpect(jsonPath("$[1].memberId").value("sdp"));
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
                .title("자바의 신")
                .description("자바 책")
                .author("")
                .build();

        bookRepository.save(book);
    }

    private void saveBook2() {
        Book book = Book.builder()
                .title("자바의 신2")
                .description("자바 책")
                .author("")
                .build();

        bookRepository.save(book);
    }
}