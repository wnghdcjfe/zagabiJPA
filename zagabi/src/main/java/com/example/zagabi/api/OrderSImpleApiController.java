package com.example.zagabi.api;

import static java.util.stream.Collectors.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.zagabi.domain.Address;
import com.example.zagabi.domain.Order;
import com.example.zagabi.domain.OrderItem;
import com.example.zagabi.domain.OrderStatus;
import com.example.zagabi.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderSImpleApiController {
	private final OrderRepository orderRepository;

	// order까지만 조회
	@GetMapping("/api/v1/orders")
	public List<SimpleOrderDto> ordersV3() {
		List<Order> orders = orderRepository.findAllWithMemberDelivery();
		List<SimpleOrderDto> result = orders.stream()
			.map(o -> new SimpleOrderDto(o))
			.collect(toList());
		return result;
	}
	//orderItem까지 조회
	@GetMapping("/api/v2/orders")
	public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "100") int limit) {
		List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
		List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(toList());
		return result;
	}

	@Data
	static class OrderDto {
		private Long orderId;
		private String name;
		private LocalDateTime orderDate; //주문시간
		private OrderStatus orderStatus;
		private Address address;
		private List<OrderItemDto> orderItems;

		public OrderDto(Order order) {
			orderId = order.getId();
			name = order.getMember().getName();
			orderDate = order.getOrderDate();
			orderStatus = order.getStatus();
			address = order.getDelivery().getAddress();
			orderItems = order.getOrderItems()
				.stream()
				.map(orderItem -> new OrderItemDto(orderItem)).collect(toList());
		}
	}

	@Data
	static class OrderItemDto {
		private String itemName;//상품 명
		private int orderPrice;
		private int count; //주문 수량

		public OrderItemDto(OrderItem orderItem) {
			itemName = orderItem.getItem().getName();
			orderPrice = orderItem.getOrderPrice();
			count = orderItem.getCount();
		}
	}

	@Data
	static class SimpleOrderDto {
		private Long orderId;
		private String name;
		private LocalDateTime orderDate; //주문시간
		private OrderStatus orderStatus;
		private Address address;

		public SimpleOrderDto(Order order) {
			orderId = order.getId();
			name = order.getMember().getName();
			orderDate = order.getOrderDate();
			orderStatus = order.getStatus();
			address = order.getDelivery().getAddress();
		}
	}
}
