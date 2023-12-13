package com.book.library.book.api;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;

@JsonTypeName(value = "book")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record BookRequestDto(String title, String author, String description) {

    @Builder
    public BookRequestDto {
    }
}
