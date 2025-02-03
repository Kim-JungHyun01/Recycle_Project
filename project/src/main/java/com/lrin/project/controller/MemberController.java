package com.lrin.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("cssPath", "member/login");
        model.addAttribute("pageTitle", "로그인");
        return "member/login";
    }
    @GetMapping(value = "/signup")
    public String signup(Model model) {
        model.addAttribute("cssPath", "member/signup");
        model.addAttribute("pageTitle", "회원가입");
        return "member/signup";
    }
    @GetMapping(value = "/mypage")
    public String mypage(Model model) {
        model.addAttribute("cssPath", "member/mypage");
        model.addAttribute("pageTitle", "마이페이지");
        return "member/mypage";
    }
}
