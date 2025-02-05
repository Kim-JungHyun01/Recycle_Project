package com.lrin.project.repository.member;

import com.lrin.project.dto.member.MemberDTO;
import com.lrin.project.entity.member.MemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    //아이디 중복 확인
    @Query(value = "select id from member where id = :id", nativeQuery = true)
    String findMember(@Param("id") String id);

    //시큐리티
    MemberEntity findOneById(String name);

    @Query(value = "select pw from member where id = :id", nativeQuery = true)
    String idPwChk(@Param("id") String id);

    //로그인 시 아이디, 비밀번호

//    @Transactional
//    @Modifying
//    @Query(value = "select name, id, pw, email_id, email_domain, tel1,tel2,tel3, jumin1, jumin2, streetaddr, addr, DETAILADDR from member" +
//            " where name=:name AND tel1 || '-' || tel2 || '-' || tel3=:tel AND email_id || '@' || email_domain  =:email", nativeQuery = true)
//    List<MemberEntity> findID(@Param("name") String name, @Param("tel") String tel, @Param("email") String email);
}