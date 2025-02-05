package com.lrin.project.controller;

import com.lrin.project.dto.member.MemberDTO;
import com.lrin.project.entity.member.MemberEntity;
import com.lrin.project.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
    @PostMapping(value = "/memberChk")//아이디 중복 확인
    public void memberChk(@RequestParam("id") String id, HttpServletResponse response) throws IOException {
        String result = memberService.findMember(id);
        PrintWriter pp = response.getWriter();
        pp.print(result);
    }
    @PostMapping(value = "/memberSave")
    public String memberSave(
            @ModelAttribute("memberDTO") @Valid MemberDTO memberDTO, BindingResult bindingResult) {
        MemberEntity mentity = memberDTO.toEntity();
        memberService.memberSave(mentity);
        return "redirect:/";
    }
    @PostMapping(value = "/idpwChk")
    public void idpwChk(@RequestParam("id") String id, @RequestParam("pw") String pw, HttpServletResponse response) throws IOException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter prw = response.getWriter();
        String result = memberService.idPwChk(id);
        boolean chk = passwordEncoder.matches(pw,result);
        prw.print(chk);
    }
    @GetMapping(value = "/mypage")
    public String mypage(Model model) {
        model.addAttribute("cssPath", "member/mypage");
        model.addAttribute("pageTitle", "마이페이지");
        model.addAttribute("jsPath", "member/member");
        return "member/mypage";
    }
}
