package org.koreait.member.repositories;

import org.koreait.member.entities.Member;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface MemberRepository extends ListCrudRepository<Member, Long> {
    boolean existsByEmail(String email);        // 회원 존재 여부
    Optional<Member> findByEmail(String email); // 회원 조회
}
