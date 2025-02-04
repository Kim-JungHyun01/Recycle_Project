package com.lrin.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CollectController {

    @GetMapping(value = "/collect")
    public String index(Model model) {
        model.addAttribute("cssPath", "collect/collect");
//        model.addAttribute("pageTitle", "메인");
        model.addAttribute("jsPath", "collect/collect");
        return "collect/collect";
    }

}
