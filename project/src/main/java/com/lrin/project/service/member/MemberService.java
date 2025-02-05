package com.lrin.project.service.member;

import com.lrin.project.entity.member.MemberEntity;

public interface MemberService {
    String findMember(String id);
    void memberSave(MemberEntity mentity);
    String idPwChk(String id);
    MemberEntity memberInfo(String name);
    void mySomeSave(String addr, String streetaddr, String detailaddr, String tel,String id);
}