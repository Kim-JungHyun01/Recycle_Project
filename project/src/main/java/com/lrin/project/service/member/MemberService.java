package com.lrin.project.service.member;

import com.lrin.project.entity.member.MemberEntity;
import java.util.List;

public interface MemberService {
    String findMember(String id);
    void memberSave(MemberEntity mentity);
    String idPwChk(String id);
}