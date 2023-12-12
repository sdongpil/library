package com.book.library.book.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class BookRent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;

    private String memberId;

    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    @Builder
    public BookRent(Long bookId, String memberId, LocalDateTime rentalDate, LocalDateTime returnDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public void returnBook() {
        this.returnDate = LocalDateTime.now();
    }
}
