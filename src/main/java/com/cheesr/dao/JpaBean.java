package com.cheesr.dao;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaBean
{
	@PersistenceContext
	private EntityManager em;

	@Resource
	private SessionContext context;

	public void setTransactionRollbackOnly()
	{
		context.setRollbackOnly();
	}

	public <T> T load(Class<T> clz, Serializable id)
	{
		return em.find(clz, id);
	}
}
