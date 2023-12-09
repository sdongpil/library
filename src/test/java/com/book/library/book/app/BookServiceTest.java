package com.book.library.book.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void t1() {

        BookRequest bookRequest = BookRequest.builder()
                .name("객체지향의 사실과 오해")
                .description("")
                .author("조영호")
                .build();

        BookResponse bookResponse = bookService.save(bookRequest);

        assertThat(bookResponse.author()).isEqualTo("조영호");
    }
}