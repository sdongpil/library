package com.book.library.member.app;

import com.book.library.exception.DuplicateMemberException;
import com.book.library.member.domain.Member;
import com.book.library.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MemberResponse join(MemberRequest memberRequest) {
        if (memberDuplicateCheck(memberRequest.memberId())) {
            throw new DuplicateMemberException();
        }

        Member member = Member.toDomain(memberRequest);

        memberRepository.save(member);

        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .password(member.getPassword())
                .name(member.getName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .age(member.getAge())
                .build();
    }

    private boolean memberDuplicateCheck(String memberId) {
        return memberRepository.existsByMemberId(memberId);
    }
}
