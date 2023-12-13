package com.book.library.book.api;

import com.book.library.book.app.BookRentResponse;
import lombok.Builder;

import java.time.LocalDateTime;

public record BookRentResponseDto(String bookTitle, String memberId, LocalDateTime rentalDate, LocalDateTime returnDate ) {

    @Builder
    public BookRentResponseDto {
    }

    public static BookRentResponseDto toResponseDto(BookRentResponse bookRentResponse) {
        return BookRentResponseDto.builder()
                .bookTitle(bookRentResponse.bookTitle())
                .memberId(bookRentResponse.memberId())
                .rentalDate(bookRentResponse.rentalDate())
                .returnDate(bookRentResponse.returnDate())
                .build();
    }
}
