package com.book.library.member.app;

import com.book.library.exception.DuplicateMemberException;
import com.book.library.member.domain.Member;
import com.book.library.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MemberRepository memberRepository;


    @BeforeEach
    void setup() {
        jdbcTemplate.execute("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
    }

    @AfterEach
    void deleteAll() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("파라미터 값 유효하면 회원가입 성공")
    void t1() {

        MemberRequest memberRequest = MemberRequest.builder()
                .memberId("sdp")
                .password("123")
                .email("pildong@naver.com")
                .name("dongpil")
                .age(30)
                .phoneNumber(010)
                .build();

        MemberResponse memberResponse = memberService.join(memberRequest);

        assertThat(memberResponse.name()).isEqualTo("dongpil");
    }

    @Test
    @DisplayName("중복된 회원일 경우 DuplicateMemberException 예외 발생")
    void t2() {
        saveMember();

        MemberRequest memberRequest = MemberRequest.builder()
                .memberId("sdp")
                .password("123")
                .email("pildong@naver.com")
                .name("dongpil")
                .age(30)
                .phoneNumber(010)
                .build();

        assertThatThrownBy(() -> memberService.join(memberRequest))
                .isInstanceOf(DuplicateMemberException.class)
                .hasMessage("이미 존재하는 회원 ID입니다.");
    }

    private void saveMember() {
        memberRepository.save(Member.builder()
                .memberId("sdp")
                .password("1234")
                .name("dongpil")
                .age(30)
                .email("pildong@naver.com")
                .phoneNumber(010)
                .build());
    }
}