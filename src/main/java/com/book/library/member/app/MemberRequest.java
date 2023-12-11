package com.book.library.member.app;

import com.book.library.member.api.MemberRequestDto;
import lombok.Builder;


public record MemberRequest (String memberId, String password, String name, int age, String email,int phoneNumber){

    @Builder
    public MemberRequest {
    }

    public static MemberRequest toRequest(MemberRequestDto memberRequestDto) {
        return MemberRequest.builder()
                .memberId(memberRequestDto.memberId())
                .password(memberRequestDto.password())
                .name(memberRequestDto.name())
                .age(memberRequestDto.age())
                .email(memberRequestDto.email())
                .phoneNumber(memberRequestDto.phoneNumber())
                .build();
    }
}
