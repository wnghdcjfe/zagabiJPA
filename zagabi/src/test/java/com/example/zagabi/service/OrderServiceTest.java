package com.example.zagabi.service;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.zagabi.domain.Address;
import com.example.zagabi.domain.Member;
import com.example.zagabi.domain.Order;
import com.example.zagabi.domain.OrderStatus;
import com.example.zagabi.domain.item.Book;
import com.example.zagabi.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
	@Autowired
	EntityManager em;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orderRepository;

	@Test
	public void 상품주문() {
		//given
		Member member = new Member();
		member.setName("aa");
		member.setAddress(new Address("a", "a", "a"));
		em.persist(member);

		Book book = new Book();
		book.setName("시골 ");
		book.setPrice(10000);
		book.setStockQuantity(10);
		em.persist(book);
		int orderCount = 2;

		//when
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

		//then
		Order getOrder = orderRepository.findOne(orderId);
		assertEquals("ㅁ", OrderStatus.ORDER, getOrder.getStatus());
	}
}