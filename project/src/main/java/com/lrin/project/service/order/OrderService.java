package com.lrin.project.service.order;

import com.lrin.project.dto.order.OrderItemDTO;
import com.lrin.project.entity.order.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(List<OrderItemDTO> orderItemsDTO, String userId);


}
