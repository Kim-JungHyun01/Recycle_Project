package com.lrin.project.controller;

import com.lrin.project.entity.item.Item;
import com.lrin.project.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ItemService itemService;
    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("cssPath", "home/index");
        model.addAttribute("pageTitle", "메인");
        model.addAttribute("jsPath", "home/index");
        return "home/index";
    }
    @GetMapping(value = "/price")
    public String price(Model model) {
        List<Item> items = itemService.getItems();
        model.addAttribute("cssPath", "home/price");
        model.addAttribute("pageTitle", "수거 가격표");
        model.addAttribute("items", items);
        return "home/price";
    }
}