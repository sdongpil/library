package com.book.library.book.app;

import com.book.library.book.domain.Book;
import com.book.library.book.repository.BookRepository;
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


    private void saveBook() {
        Book book = Book.builder()
                .name("자바의 신")
                .description("자바 책")
                .author("")
                .build();

        bookRepository.save(book);
    }
}