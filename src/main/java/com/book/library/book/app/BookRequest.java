package com.book.library.book.app;

import com.book.library.book.api.BookRequestDto;
import lombok.Builder;

public record BookRequest (String title, String author, String description) {

    @Builder
    public BookRequest {
    }

    public static BookRequest toRequest(BookRequestDto bookRequestDto) {
        return BookRequest.builder()
                .title(bookRequestDto.title())
                .author(bookRequestDto.author())
                .description(bookRequestDto.description())
                .build();
    }
}
