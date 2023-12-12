package com.book.library.exception;

public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException() {
        super("이미 존재하는 회원 ID입니다.");
    }
}