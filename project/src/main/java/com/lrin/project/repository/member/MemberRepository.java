package com.lrin.project.repository.member;

import com.lrin.project.dto.member.MemberDTO;
import com.lrin.project.entity.member.MemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    @Transactional
    @Query(value = "select id from usermember where id = :id", nativeQuery = true)
    String findMember(@Param("id") String id);
}