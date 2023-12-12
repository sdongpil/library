package com.book.library.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("도서를 찾을 수 없습니다.");
    }
}
