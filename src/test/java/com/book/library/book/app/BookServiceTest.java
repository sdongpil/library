package com.book.library.book.app;

import com.book.library.book.domain.Book;
import com.book.library.book.domain.BookRent;
import com.book.library.book.repository.BookRentRepository;
import com.book.library.book.repository.BookRepository;
import com.book.library.exception.BookRentNotFoundException;
import com.book.library.member.domain.Member;
import com.book.library.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@ActiveProfiles("test")
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRentRepository bookRentRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setup() {
        jdbcTemplate.execute("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE book ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE book_rent ALTER COLUMN id RESTART WITH 1");
    }

    @AfterEach
    void deleteAll() {
        bookRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        bookRentRepository.deleteAllInBatch();
    }

    @Test
    @Transactional
    @DisplayName("BookRequest값 유효하면 도서 저장 성공")
    void t1() {
        BookRequest bookRequest = BookRequest.builder()
                .title("객체지향의 사실과 오해")
                .description("")
                .author("조영호")
                .build();

        BookResponse bookResponse = bookService.save(bookRequest);

        assertThat(bookResponse.author()).isEqualTo("조영호");
    }

    @Test
    @Transactional
    @DisplayName("도서 id와 BookRequest값 유효하면 도서 수정 성공")
    void t2() {
        saveBook();

        BookRequest bookRequest = BookRequest.builder()
                .title("자바의 신2")
                .author("이상민2")
                .description("자바 책2")
                .build();

        BookResponse bookResponse = bookService.update(1L, bookRequest);

        assertThat(bookResponse.author()).isEqualTo("이상민2");
    }

    @Test
    @Transactional
    @DisplayName("bookId, memberId 유효하면 대여 성공")
    void t3() {
        saveBook();
        saveMember();

        bookService.rent(1L, 1L);
        BookRent bookRent = bookRentRepository.findById(1L).orElseThrow(BookRentNotFoundException::new);

        assertThat(bookRent.getBookId()).isEqualTo(1L);
    }

    @Test
    @Transactional
    @DisplayName("회원아이디와 도서아이디 유효하면 도서 반납 성공")
    void t4() {
        saveBook();
        saveMember();
        bookService.rent(1L,1L);

        bookService.returnBook(1L,1L);

        BookRent bookRent = bookRentRepository.findById(1L).orElseThrow(BookRentNotFoundException::new);

        assertThat(bookRent.getReturnDate()).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName("대출한 도서가 없으면 예외 발생 , 메세지 출력")
    void t5() {
        saveBook();
        saveMember();

        assertThatThrownBy(() -> bookService.returnBook(1L, 1L))
                .isInstanceOf(BookRentNotFoundException.class)
                .hasMessage("대출한 도서가 없습니다.");
    }

    @Test
    @Transactional
    @DisplayName("도서 대출 목록 가져오기")
    void t6() {
        saveBook();
        saveBook2();
        saveMember();
        bookService.rent(1L,1L);
        bookService.rent(2L,1L);

        List<BookRentResponse> bookRentHistory = bookService.getBookRentHistory("sdp");

        assertThat(bookRentHistory).hasSize(2)
                .extracting("bookTitle", "memberId")
                .containsExactlyInAnyOrder(
                        tuple("자바의 신", "sdp"),
                        tuple("자바의 신2", "sdp")
                );
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