package com.lrin.project.service.member;

import com.lrin.project.dto.member.MemberDTO;
import com.lrin.project.entity.member.MemberEntity;
import com.lrin.project.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MemberServiceImple implements MemberService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String findMember(String id) {
        return memberRepository.findMember(id);
    }

    @Override
    public void memberSave(MemberEntity mentity) {
        mentity.setPw(bCryptPasswordEncoder.encode(mentity.getPw()));
        memberRepository.save(mentity);
    }

    @Override
    public String idPwChk(String id) {
        return memberRepository.idPwChk(id);
    }
}