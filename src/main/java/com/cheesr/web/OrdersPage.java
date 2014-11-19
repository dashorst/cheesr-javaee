package com.cheesr.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import com.cheesr.entities.Order;

public class OrdersPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public OrdersPage() {
		add(new ListView<Order>("items", new OrdersModel()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Order> item) {
				Order o = item.getModelObject();
				item.add(new Label("id", o.getId()));
				item.add(new Label("name", o.getName()));
				item.add(new Label("total", o.getTotal()));
			}
		});
	}
}
