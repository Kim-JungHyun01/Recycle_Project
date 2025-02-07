package com.lrin.project.controller;

import com.lrin.project.dto.order.OrderDTO;
import com.lrin.project.dto.order.OrderItemDTO;
import com.lrin.project.entity.item.Item;
import com.lrin.project.entity.order.Order;
import com.lrin.project.service.item.ItemService;
import com.lrin.project.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final ItemService itemService;
    private final OrderService orderService;

    // 수거 페이지로 가기
    @GetMapping(value = "/collect")
    public String index(Model model) {

        List<Item> items = itemService.getItems();

        model.addAttribute("items", items);
        model.addAttribute("cssPath", "order/collect");
//        model.addAttribute("pageTitle", "메인");
        model.addAttribute("jsPath", "order/collect");
        return "order/collect";
    }

    @PostMapping("/order")
    public String saveOrder(@ModelAttribute OrderDTO orderDTO,  Principal principal) {
        List<OrderItemDTO> orderItemsDTO = orderDTO.getOrderItems();
        String userId = principal.getName();

        Order order = orderService.createOrder(orderItemsDTO, userId);

        return "redirect:/collect";
    }





}
