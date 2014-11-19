package com.cheesr.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import com.cheesr.entities.Order;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderDao implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	public void save(Order order) {
		em.persist(order);
	}

	@SuppressWarnings("unchecked")
	public List<Order> list() {
		CriteriaQuery<Order> criteria = em.getCriteriaBuilder().createQuery(
				Order.class);
		criteria.from(Order.class);
		Query query = em.createQuery(criteria);
		return query.getResultList();
	}
}
