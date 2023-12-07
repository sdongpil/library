package com.book.library.member.api;

import com.book.library.member.app.MemberRequest;
import com.book.library.member.app.MemberResponse;
import com.book.library.member.app.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberResponseDto> join(MemberRequestDto memberRequestDto) {

        MemberRequest memberRequest = MemberRequest.toRequest(memberRequestDto);

        MemberResponse memberResponse = memberService.join(memberRequest);

        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .email(memberResponse.email())
                .name(memberRequestDto.name())
                .age(memberRequestDto.age())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(memberResponseDto);
    }
}
