package com.book.library.book.app;

import lombok.Builder;

public record BookResponse (String name, String author, String description) {

    @Builder
    public BookResponse {
    }
}
