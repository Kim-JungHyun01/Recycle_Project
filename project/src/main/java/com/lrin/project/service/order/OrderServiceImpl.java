package com.lrin.project.service.order;

import com.lrin.project.constant.OrderStatus;
import com.lrin.project.dto.order.OrderItemDTO;
import com.lrin.project.entity.item.Item;
import com.lrin.project.entity.member.MemberEntity;
import com.lrin.project.entity.order.Order;
import com.lrin.project.entity.order.OrderItem;
import com.lrin.project.repository.item.ItemRepository;
import com.lrin.project.repository.member.MemberRepository;
import com.lrin.project.repository.order.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Override
    public Order createOrder(List<OrderItemDTO> orderItemsDTO, String userId) {

        Order order = new Order();

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDTO dto : orderItemsDTO) {
            // Item 엔티티 찾기
            Item item = itemRepository.findById(dto.getItemName())
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

            // OrderItem 엔티티 생성 및 값 설정
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(dto.getCount());
            orderItem.setOrderPrice(dto.getOrderPrice());
            orderItem.setOrder(order);

            // OrderItem을 Order에 추가
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        MemberEntity member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        order.setMember(member);
        order.setOrderStatus(OrderStatus.ORDER);

        // Order 저장
        return orderRepository.save(order);
    }


}
