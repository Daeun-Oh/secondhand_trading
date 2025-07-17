package org.koreait.member.repositories;

import org.koreait.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository
        extends JpaRepository<Member, Long>,
                QuerydslPredicateExecutor<Member> {

    boolean existsByEmail(String email);        // 회원 존재 여부
    Optional<Member> findByEmail(String email); // 회원 조회
}
