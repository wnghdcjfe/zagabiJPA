package com.example.zagabi.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.example.zagabi.domain.Member;
import com.example.zagabi.domain.Order;
import com.example.zagabi.domain.OrderSearch;
import com.example.zagabi.domain.OrderStatus;
import com.example.zagabi.domain.QMember;
import com.example.zagabi.domain.QOrder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
	private final EntityManager em;

	public void save(Order order) {
		em.persist(order);
	}

	public Order findOne(Long id) {
		return em.find(Order.class, id);
	}

	public List<Order> findAll(OrderSearch orderSearch) {
		QOrder order = QOrder.order;
		QMember member = QMember.member;
		JPAQueryFactory query = new JPAQueryFactory(em);
		return query.select(order)
			.from(order)
			.join(order.member, member)
			.where(statusEq(orderSearch.getOrderStatus()),
				nameLike(orderSearch.getMemberName())).limit(1000)
			.fetch();
	}

	private BooleanExpression statusEq(OrderStatus statusCond) {
		if (statusCond == null) {
			return null;
		}
		return QOrder.order.status.eq(statusCond);
	}

	private BooleanExpression nameLike(String nameCond) {
		if (!StringUtils.hasText(nameCond)) {
			return null;
		}
		return QMember.member.name.like(nameCond);
	}

	public List<Order> findAllWithMemberDelivery() {
		return em.createQuery(
			"select o from Order o" +
				" join fetch o.member m" +
				" join fetch o.delivery d", Order.class).getResultList();
	}

	public List<Order> findAllWithMemberDelivery(int offset, int limit) {
		return em.createQuery(
			"select o from Order o" +
				" join fetch o.member m" +
				" join fetch o.delivery d", Order.class)
			.setFirstResult(offset)
			.setMaxResults(limit)
			.getResultList();
	}
}
