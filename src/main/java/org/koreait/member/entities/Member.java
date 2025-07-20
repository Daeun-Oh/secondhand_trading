package org.koreait.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.global.entities.BaseEntity;
import org.koreait.member.constants.Authority;
import org.koreait.member.social.constants.SocialType;

import java.time.LocalDateTime;

@Data
@Entity
@Table(indexes = {
        @Index(name = "idx_member_created_at", columnList = "createdAt DESC"),
        @Index(name = "idx_member_name", columnList = "name"),
        @Index(name = "idx_member_mobile", columnList = "mobile"),
        // 관리자 페이지에서 카카오, 네이버 로그인 멤버를 분류해서 보고 싶을 때는 socialType만 사용할 수 있다. 보통은 아래와 같이 Token과 같이 사용.
        @Index(name = "idx_member_social", columnList = "socialType, socialToken")
})
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private Long seq;

    @Column(unique = true, nullable = false, length = 75)
    private String email;

    // password가 nullable인 이유: 소셜 로그인 때는 굳이 비밀번호를 채우지 않기 때문
    @Column(length = 65)
    private String password;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 15)
    private String mobile;

    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.MEMBER;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(length = 65)
    private String socialToken;

    private boolean termsAgree;

    private boolean locked;         // 계정 중지 상태인지

    private LocalDateTime expired;  // 계정 만료 일자, null이면 만료 X

    private LocalDateTime credentialChangedAt;  // 비밀번호 변경 일시
}
