package com.book.library.member.app;

import lombok.Builder;

public record MemberResponse(String name, int age, String email, int phoneNumber) {

    @Builder
    public MemberResponse {
    }
}
