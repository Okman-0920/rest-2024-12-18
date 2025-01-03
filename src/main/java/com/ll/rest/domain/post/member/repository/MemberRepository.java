package com.ll.rest.domain.post.member.repository;

import com.ll.rest.domain.post.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
