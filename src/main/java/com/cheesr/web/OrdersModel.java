package com.cheesr.web;

import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.wicket.cdi.NonContextual;
import org.apache.wicket.model.LoadableDetachableModel;

import com.cheesr.dao.OrderDao;
import com.cheesr.entities.Order;

public class OrdersModel extends LoadableDetachableModel<List<Order>> {
	private static final long serialVersionUID = 1L;

	@Inject
	private OrderDao orders;

	public OrdersModel() {
		BeanProvider.injectFields(this);
	}

	@Override
	protected List<Order> load() {
		return orders.list();
	}
}
