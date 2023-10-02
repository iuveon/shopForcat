package com.forcat.shop.service;

import com.forcat.shop.constant.ItemSellStatus;
import com.forcat.shop.dto.OrderDto;
import com.forcat.shop.entity.Item;
import com.forcat.shop.entity.Member;
import com.forcat.shop.entity.Order;
import com.forcat.shop.entity.OrderItem;
import com.forcat.shop.exception.repository.ItemRepository;
import com.forcat.shop.exception.repository.MemberRepository;
import com.forcat.shop.exception.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    public Item saveItem() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return itemRepository.save(item);
    } // 테스트를 위한 주문 상품 저장 메소드

    public Member saveMember() {
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    } // 테스트를 위한 회원 정보 저장 메소드

    @Test
    @DisplayName("주문 테스트")
    public void order() {
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        Long orderId = orderService.order(orderDto, member.getEmail());
        // 주문 로직 호출 결과로 리턴된 주문번호를 orderId에 저장
        Order order = orderRepository.findById(orderId)
                                     .orElseThrow(EntityNotFoundException::new);
        // 주문 번호를 이용하여 저장된 주문 정보 조회
        List<OrderItem> orderItems = order.getOrderItems();
        int totalPrice = orderDto.getCount() * item.getPrice();
        // 주문한 상품의 총 가격 계산
        assertEquals(totalPrice, order.getTotalPrice());
        // 주문한 상품의 총 가격과 DB에 저장된 상품 가격이 같은가
    }

}
