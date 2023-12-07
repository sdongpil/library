package com.book.library.member.api;

import lombok.Builder;

public record MemberRequestDto(String name, int age, String email, int phoneNumber) {

}
