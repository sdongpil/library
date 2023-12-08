package com.book.library.member.repository;

import com.book.library.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원정보 파라미터 값 유효하면 저장 성공")
    void t1() {

        Member dongpil = Member.builder()
                .name("dongpil")
                .email("pildong@naver.com")
                .age(30)
                .phoneNumber(0105555)
                .build();

        Member member = memberRepository.save(dongpil);

        assertThat(member.getName()).isEqualTo("dongpil");
    }
}