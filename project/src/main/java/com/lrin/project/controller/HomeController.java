package com.lrin.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("cssPath", "home/index");
        model.addAttribute("pageTitle", "메인페이지");
        model.addAttribute("jsPath", "home/index");
        return "home/index";
    }
}
