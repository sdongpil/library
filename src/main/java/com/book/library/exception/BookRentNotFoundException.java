package com.book.library.exception;

public class BookRentNotFoundException extends RuntimeException {
    public BookRentNotFoundException() {
        super("대출한 도서가 없습니다.");
    }
}
