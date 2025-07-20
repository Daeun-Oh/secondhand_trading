package org.koreait.member.repositories;

import org.koreait.member.entities.Member;
import org.koreait.member.social.constants.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository
        extends JpaRepository<Member, Long>,
                QuerydslPredicateExecutor<Member> {

    boolean existsByEmail(String email);        // 회원 존재 여부
    
    Optional<Member> findByEmail(String email); // 회원 조회
    
    // 소셜 타입, 토큰 매치 성공 여부
    boolean existsBySocialTypeAndSocialToken(SocialType type, String token);
    
    // 소셜 타입, 토큰 매치된 멤버 정보
    Member findBySocialTypeAndSocialToken(SocialType type, String token);
}
