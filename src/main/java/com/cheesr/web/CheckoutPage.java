package com.cheesr.web;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.cheesr.dao.OrderDao;
import com.cheesr.domain.Cart;
import com.cheesr.entities.Cheese;
import com.cheesr.entities.Order;
import com.cheesr.entities.OrderItem;

public class CheckoutPage extends WebPage {
	private static final long serialVersionUID = 1L;

	@EJB
	private OrderDao orders;

	@Inject
	private Cart cart;

	private Order order = new Order();

	public CheckoutPage() {
		add(new FeedbackPanel("feedback"));
		Form<Void> form = new Form<Void>("form") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				if (cart.getItems().isEmpty()) {
					throw new RestartResponseException(OrdersPage.class);
				}

				for (Cheese cheese : cart.getItems()) {
					OrderItem item = new OrderItem();
					item.setOrder(order);
					item.setCheese(cheese);
					order.getItems().add(item);
				}
				orders.save(order);
				setResponsePage(Index.class);
			}
		};
		add(form);

		TextField<String> nameField = new TextField<>("name", PropertyModel.of(
				this, "order.name"));
		nameField.add(new PropertyValidator<>());
		form.add(nameField);

		TextField<String> streetField = new TextField<>("street",
				PropertyModel.of(this, "order.street"));
		streetField.add(new PropertyValidator<>());
		form.add(streetField);

		form.add(new TextField<>("zipcode", PropertyModel.of(this,
				"order.zipcode")).add(new PropertyValidator<>()));
		form.add(new TextField<>("city", PropertyModel.of(this, "order.city"))
				.add(new PropertyValidator<>()));
		form.add(new TextField<>("country", PropertyModel.of(this,
				"order.country")).add(new PropertyValidator<>()));

		add(new ListView<Cheese>("item", cart.getItems()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Cheese> item) {
				Cheese c = item.getModelObject();
				item.add(new Label("name", c.getName()));
				item.add(new Label("price", c.getPrice()));
				item.add(new Link<Cheese>("remove", item.getModel()) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						Cheese cheese = getModelObject();
						cart.getItems().remove(cheese);
					}
				});
			}
		});
		add(new Label("total", PropertyModel.of(this, "cart.total")));
	}
}
