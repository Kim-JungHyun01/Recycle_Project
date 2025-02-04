package com.lrin.project.controller;

import com.lrin.project.dto.member.MemberDTO;
import com.lrin.project.entity.member.MemberEntity;
import com.lrin.project.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    MemberService memberService;

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("cssPath", "member/login");
        model.addAttribute("pageTitle", "로그인");
        model.addAttribute("jsPath", "member/member");
        return "member/login";
    }
    @GetMapping(value = "/signup")
    public String signup(Model model) {
        model.addAttribute("cssPath", "member/signup");
        model.addAttribute("pageTitle", "회원가입");
        model.addAttribute("jsPath", "member/member");
        return "member/signup";
    }
    @GetMapping(value = "/mypage")
    public String mypage(Model model) {
        model.addAttribute("cssPath", "member/mypage");
        model.addAttribute("pageTitle", "마이페이지");
        model.addAttribute("jsPath", "member/member");
        return "member/mypage";
    }
    @PostMapping(value = "/memberChk")//아이디 중복 확인
    public void memberChk(@RequestParam("id") String id, HttpServletResponse response) throws IOException {
        String result = memberService.findMember(id);
        PrintWriter pp = response.getWriter();
        pp.print(result);
    }
}
