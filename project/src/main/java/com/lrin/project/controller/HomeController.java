package com.lrin.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("cssPath", "home/index");
        model.addAttribute("pageTitle", "메인");
        model.addAttribute("jsPath", "home/index");
        return "home/index";
    }
    @GetMapping(value = "/price")
    public String price(Model model) {
        model.addAttribute("cssPath", "home/price");
        model.addAttribute("pageTitle", "수거 가격표");
        return "home/price";
    }
}
