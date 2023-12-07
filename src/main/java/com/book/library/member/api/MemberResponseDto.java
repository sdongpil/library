package com.book.library.member.api;

import lombok.Builder;

public record MemberResponseDto(String name, int age, String email, int phoneNumber) {

    @Builder
    public MemberResponseDto {
    }
}
