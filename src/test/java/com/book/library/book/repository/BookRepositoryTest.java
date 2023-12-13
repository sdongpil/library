package com.book.library.book.repository;

import com.book.library.book.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void t1() {

        Book book = Book.builder()
                .title("토비의 스프링")
                .author("토비")
                .description("스프링이란")
                .build();

        Book saveBook = bookRepository.save(book);

        assertThat(saveBook.getTitle()).isEqualTo("토비의 스프링");
    }
}