package com.example.zagabi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.zagabi.domain.Delivery;
import com.example.zagabi.domain.Member;
import com.example.zagabi.domain.Order;
import com.example.zagabi.domain.OrderItem;
import com.example.zagabi.domain.OrderSearch;
import com.example.zagabi.domain.item.Item;
import com.example.zagabi.repository.ItemRepository;
import com.example.zagabi.repository.MemberRepository;
import com.example.zagabi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	@Transactional
	public Long order(Long memberId, Long itemId, int count){
		Member member = memberRepository.findById(memberId).get();
		Item item = itemRepository.findOne(itemId);
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());

		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		Order order = Order.createOrder(member, delivery, orderItem);

		orderRepository.save(order);
		return order.getId();
	}

	@Transactional
	public void cancelOrder(Long orderId){
		Order order = orderRepository.findOne(orderId);
		order.cancel();
	}

	public List<Order> findOrders(OrderSearch orderSearch) {
		return orderRepository.findAll(orderSearch);
	}
}
