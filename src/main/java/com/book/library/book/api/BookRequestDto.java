package com.book.library.book.api;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record BookRequestDto(String name, String author, String description) {

    @Builder
    public BookRequestDto {
    }
}
