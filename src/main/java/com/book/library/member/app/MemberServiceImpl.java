package com.book.library.member.app;

import com.book.library.member.domain.Member;
import com.book.library.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public MemberResponse join(MemberRequest memberRequest) {
        // TODO: 12/7/23 회원 중복체크

        Member member = Member.toDomain(memberRequest);

        memberRepository.save(member);

        return MemberResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .age(member.getAge())
                .build();
    }
}
