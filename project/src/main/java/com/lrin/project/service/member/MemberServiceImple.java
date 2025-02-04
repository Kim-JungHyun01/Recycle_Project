package com.lrin.project.service.member;

import com.lrin.project.dto.member.MemberDTO;
import com.lrin.project.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImple implements MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    public String findMember(String id) {
        return memberRepository.findMember(id);
    }
}