package com.book.library.member.domain;

import com.book.library.member.app.MemberRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int phoneNumber;

    @Builder
    public Member(String name, int age, String email, int phoneNumber) {
        Assert.notNull(name," 이름은 필수 값 입니다.");
        Assert.notNull(email," 이메일은 필수 값 입니다.");
        Assert.notNull(phoneNumber," 핸드폰번호는 필수 값 입니다.");

        this.name = name;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public static Member toDomain(MemberRequest memberRequest) {
        return Member.builder()
                .name(memberRequest.name())
                .age(memberRequest.age())
                .email(memberRequest.email())
                .phoneNumber(memberRequest.phoneNumber())
                .build();
    }
}
