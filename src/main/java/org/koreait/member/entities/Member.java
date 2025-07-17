package org.koreait.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.global.entities.BaseEntity;
import org.koreait.member.constants.Authority;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Entity
@Table(indexes = {
        @Index(name = "idx_member_created_at", columnList = "createdAt DESC"),
        @Index(name = "idx_member_name", columnList = "name"),
        @Index(name = "idx_member_mobile", columnList = "mobile")})
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private Long seq;

    @Column(unique = true, nullable = false, length = 75)
    private String email;

    @Column(nullable = false, length = 65)
    private String password;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 15)
    private String mobile;

    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.MEMBER;

    @Column(name = "termsAgree")
    private boolean termsAgree;

    private boolean locked;         // 계정 중지 상태인지
    private LocalDateTime expired;  // 계정 만료 일자, null이면 만료 X

    @Column(name = "credentialChangedAt")
    private LocalDateTime credentialChangedAt;  // 비밀번호 변경 일시
}
