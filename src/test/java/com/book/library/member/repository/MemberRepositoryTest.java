package com.book.library.member.repository;

import com.book.library.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
    }

    @AfterEach
    void deleteAll() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원정보 파라미터 값 유효하면 저장 성공")
    void t1() {

        Member dongpil = Member.builder()
                .memberId("sdp2")
                .password("1234")
                .name("dongpil")
                .email("pildong@naver.com")
                .age(30)
                .phoneNumber(0105555)
                .build();

        Member member = memberRepository.save(dongpil);

        assertThat(member.getName()).isEqualTo("dongpil");
    }
}