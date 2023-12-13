package com.book.library.book.app;

import com.book.library.book.domain.BookRent;
import lombok.Builder;

import java.time.LocalDateTime;

public record BookRentResponse(String bookTitle, String memberId, LocalDateTime rentalDate, LocalDateTime returnDate ) {

    @Builder
    public BookRentResponse {
    }

    public static BookRentResponse toResponse(BookRent bookRent, String bookTitle) {
        return BookRentResponse.builder()
                .bookTitle(bookTitle)
                .memberId(bookRent.getMemberId())
                .rentalDate(bookRent.getRentalDate())
                .returnDate(bookRent.getReturnDate())
                .build();
    }
}
