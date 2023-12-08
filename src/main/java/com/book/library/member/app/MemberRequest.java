package com.book.library.member.app;

import com.book.library.member.api.MemberRequestDto;
import lombok.Builder;


public record MemberRequest (String name, int age, String email,int phoneNumber){

    @Builder
    public MemberRequest {
    }

    public static MemberRequest toRequest(MemberRequestDto memberRequestDto) {
        return MemberRequest.builder()
                .name(memberRequestDto.name())
                .age(memberRequestDto.age())
                .email(memberRequestDto.email())
                .phoneNumber(memberRequestDto.phoneNumber())
                .build();
    }
}
