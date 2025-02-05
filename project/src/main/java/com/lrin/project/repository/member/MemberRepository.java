package com.lrin.project.repository.member;

import com.lrin.project.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    //아이디 중복 확인
    @Query(value = "select id from member where id = :id", nativeQuery = true)
    String findMember(@Param("id") String id);

    //시큐리티
    MemberEntity findOneById(String name);

    @Query(value = "select pw from member where id = :id", nativeQuery = true)
    String idPwChk(@Param("id") String id);

    @Query(value = "select id, pw, name, addr, streetaddr, detailaddr, tel, reg_time, update_time, created_by, modified_by from member where id = :id", nativeQuery = true)
    MemberEntity memberInfo(@Param("id") String id);

    @Query(value = "UPDATE member SET " +
            "addr = :addr, " +
            "streetaddr = :streetaddr, " +
            "detailaddr = :detailaddr, " +
            "tel = :tel " +
            "WHERE id = :id", nativeQuery = true)
    void myUpdate(
            @Param(value = "addr") String addr,
            @Param(value = "streetaddr") String streetaddr,
            @Param(value = "detailaddr") String detailaddr,
            @Param(value = "tel") String tel,
            @Param(value = "id") String id
    );
}