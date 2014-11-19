package com.cheesr.dao;

import static javax.ejb.TransactionAttributeType.*;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import com.cheesr.entities.Cheese;

@Stateless
@TransactionAttribute(REQUIRED)
public class CheeseDao implements Serializable
{
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Cheese> getCheeses()
	{
		CriteriaQuery<Cheese> criteria = em.getCriteriaBuilder().createQuery(Cheese.class);
		criteria.from(Cheese.class);
		Query query = em.createQuery(criteria);
		return query.getResultList();
	}

	public Cheese byCode(String code)
	{
		Query query = em.createQuery("select c from Cheese c where c.id=:id");
		query.setParameter("id", code);
		return (Cheese) query.getResultList().get(0);
	}

	public void save(Cheese cheese)
	{
		// em.merge(cheese);
	}
}
