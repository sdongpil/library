package com.book.library.book.api;

import lombok.Builder;

public record BookResponseDto (String name, String author, String description) {

    @Builder
    public BookResponseDto {
    }
}
