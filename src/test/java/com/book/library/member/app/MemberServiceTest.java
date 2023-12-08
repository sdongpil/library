package com.book.library.member.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("파라미터 값 유효하면 회원가입 성공")
    void t1() {

        MemberRequest memberRequest = MemberRequest.builder()
                .email("pildong@naver.com")
                .name("dongpil")
                .age(30)
                .phoneNumber(010)
                .build();

        MemberResponse memberResponse = memberService.join(memberRequest);

        assertThat(memberResponse.name()).isEqualTo("dongpil");
    }
}