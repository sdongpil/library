package com.book.library.book.app;

import com.book.library.book.domain.Book;
import com.book.library.book.domain.BookRent;
import com.book.library.book.repository.BookRentRepository;
import com.book.library.book.repository.BookRepository;
import com.book.library.member.domain.Member;
import com.book.library.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    @Transactional
    @DisplayName("BookRequest값 유효하면 도서 저장 성공")
    void t1() {
        BookRequest bookRequest = BookRequest.builder()
                .name("객체지향의 사실과 오해")
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
                .name("자바의 신2")
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
        BookRent bookRent = bookRentRepository.findById(1L).orElseThrow();

        assertThat(bookRent.getBookId()).isEqualTo(1L);
    }

    private void saveMember() {
        memberRepository.save(Member.builder()
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