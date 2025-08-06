package org.koreait.annotations;

import org.koreait.member.MemberInfo;
import org.koreait.member.entities.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @MockMember 애너테이션을 해석해
 * Spring Security 테스트용 SecurityContext를 생성하는 팩토리 클래스
 *
 * 테스트 메서드 실행 전 호출되어,
 * 가짜(Member) 사용자 정보를 담은 인증 객체를 SecurityContext에 주입합니다.
 */
public class MockSecurityContextFactory implements WithSecurityContextFactory<MockMember> {
    @Override
    public SecurityContext createSecurityContext(MockMember annotation) {
        // 1. @MockMember 애너테이션 값으로 Member 엔티티 생성
        Member member = new Member();
        member.setSeq(annotation.seq());
        member.setEmail(annotation.email());
        member.setPassword(annotation.password());
        member.setName(annotation.name());
        member.setAuthority(annotation.authority());
        member.setTermsAgree(annotation.termsAgree());
        member.setExpired(null);
        member.setCredentialChangedAt(LocalDateTime.now().plusMonths(1L));

        // 2. 권한 목록 생성 (Spring Security용 GrantedAuthority)
        List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(
                        member.getAuthority().name()
                    )
                );

        // 3. Member 엔티티를 포함하는 인증 주체(MemberInfo) 생성
        MemberInfo memberInfo = MemberInfo
                .builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();

        // 4. 인증 토큰 생성 (principal, credentials, authorities)
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        memberInfo,
                        member.getPassword(),
                        authorities
                );

        // 5. 비어있는 SecurityContext 생성 후 인증 객체 설정
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // 6. 완성된 SecurityContext 반환 → 테스트 실행 시 자동 주입됨
        context.setAuthentication(token);

        return context;
    }
}
