package com.cheesr.web;

import javax.ejb.EJB;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.cheesr.dao.CheeseDao;
import com.cheesr.dao.JpaBean;
import com.cheesr.entities.Cheese;

public class CheeseEditPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	@EJB
	private JpaBean context;

	@EJB
	private CheeseDao cheeseDao;

	public CheeseEditPage(Cheese c)
	{

		add(new Label("cheeseId", c.getName()));
		add(new FeedbackPanel("feedback"));

		Form<Cheese> form = new Form<Cheese>("form", new LoadableEntityModel<>(c, c.getId()))
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit()
			{
				Cheese cheese = getModelObject();
				if (cheese.getPrice() < 1)
				{
					// context.setTransactionRollbackOnly();
					error("Price must be €1,- or higher");
				}
				else
				{
					cheeseDao.save(cheese);
					setResponsePage(Index.class);
				}
			}
		};
		add(form);

		form.add(new TextField<>("name", PropertyModel.of(form.getModel(), "name")));
		form.add(new TextArea<>("description", PropertyModel.of(form.getModel(), "description")));
		form.add(new NumberTextField<Double>("price", PropertyModel.of(form.getModel(), "price"),
			Double.class));
	}
}
