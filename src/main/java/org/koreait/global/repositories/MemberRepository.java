package org.koreait.global.repositories;

import org.koreait.member.entities.Member;
import org.springframework.data.repository.ListCrudRepository;

public interface MemberRepository extends ListCrudRepository<Member, Long> {

}
