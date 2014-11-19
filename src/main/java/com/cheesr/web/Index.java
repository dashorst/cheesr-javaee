package com.cheesr.web;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

import com.cheesr.dao.CheeseDao;
import com.cheesr.domain.Cart;
import com.cheesr.entities.Cheese;

public class Index extends WebPage
{
	private static final long serialVersionUID = 1L;

	@EJB
	private CheeseDao cheeseDao;

	@Inject
	private Conversation shopping;

	@Inject
	private Cart cart;

	public Index()
	{
		add(new Label("message", new MessageModel()));

		if (shopping.isTransient())
			shopping.begin();

		List<Cheese> cheeses = cheeseDao.getCheeses();

		add(new ListView<Cheese>("cheese", cheeses)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Cheese> item)
			{
				Cheese c = item.getModelObject();
				item.add(new Label("name", c.getName()));
				item.add(new Label("description", c.getDescription()));
				item.add(new Label("price", c.getPrice()));
				item.add(new Link<Cheese>("add", item.getModel())
				{
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick()
					{
						Cheese cheese = getModelObject();
						cart.getItems().add(cheese);
					}
				});
				item.add(new Link<Cheese>("edit", item.getModel())
				{
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick()
					{
						Cheese cheese = getModelObject();
						setResponsePage(new CheeseEditPage(cheese));
					}
				});
			}
		});

		add(new ListView<Cheese>("item", cart.getItems())
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Cheese> item)
			{
				Cheese c = item.getModelObject();
				item.add(new Label("name", c.getName()));
				item.add(new Label("price", c.getPrice()));
				item.add(new Link<Cheese>("remove", item.getModel())
				{
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick()
					{
						Cheese cheese = getModelObject();
						cart.getItems().remove(cheese);
					}
				});
			}
		});
		add(new Label("total", PropertyModel.of(this, "cart.total")));
		add(new Link<Void>("checkout")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(new CheckoutPage());
			}
		});
	}
}
