package com.book.library.member.api;

import lombok.Builder;

public record MemberRequestDto(String memberId, String password, String name, int age, String email, int phoneNumber) {

    @Builder
    public MemberRequestDto {
    }
}
