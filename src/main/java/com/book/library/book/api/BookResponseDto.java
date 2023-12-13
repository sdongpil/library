package com.book.library.book.api;

import lombok.Builder;

public record BookResponseDto (String title, String author, String description) {

    @Builder
    public BookResponseDto {
    }
}
