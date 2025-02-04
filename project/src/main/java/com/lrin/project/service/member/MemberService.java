package com.lrin.project.service.member;

import com.lrin.project.dto.member.MemberDTO;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    String findMember(String id);
}