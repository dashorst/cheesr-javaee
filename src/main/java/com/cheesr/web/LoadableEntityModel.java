package com.cheesr.web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;

import com.cheesr.dao.JpaBean;

public class LoadableEntityModel<T> extends LoadableDetachableModel<T>
{
	private static final long serialVersionUID = 1L;

	@Inject
	private JpaBean jpa;

	private Class<T> clz;

	private Serializable id;

	@SuppressWarnings("unchecked")
	public LoadableEntityModel(T t, Serializable id)
	{
		BeanProvider.injectFields(this);
		this.clz = (Class<T>) t.getClass();
		this.id = id;
	}

	@Override
	protected T load()
	{
		return jpa.load(clz, id);
	}
}
