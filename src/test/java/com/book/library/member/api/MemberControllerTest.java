package com.book.library.member.api;

import com.book.library.member.domain.Member;
import com.book.library.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    @Transactional
    @DisplayName("memberRequestDto 유효할 경우 회원가입성공")
    void t1() throws Exception {
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .memberId("sdp")
                .password("1234")
                .name("dongpil")
                .age(30)
                .email("pildong@naver.com")
                .phoneNumber(01011112222)
                .build();

        String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(memberRequestDto);

        mockMvc.perform(post("/api/members").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("dongpil"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.email").value("pildong@naver.com"));
    }

    @Test
    @Transactional
    @DisplayName("중복된 회원일 경우 DuplicateMemberException 발생 및 HTTP409 응답 ")
    void t2() throws Exception {
        saveMember();

        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .memberId("sdp")
                .password("1234")
                .name("dongpil")
                .age(30)
                .email("pildong@naver.com")
                .phoneNumber(010)
                .build();

        String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(memberRequestDto);

        mockMvc.perform(post("/api/members").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("이미 존재하는 회원 ID입니다."));
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
