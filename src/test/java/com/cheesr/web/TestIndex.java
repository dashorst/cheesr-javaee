package com.cheesr.web;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;


import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import com.cheesr.dao.CheeseDao;
import com.cheesr.domain.Cart;
import com.cheesr.entities.Cheese;


public class TestIndex
{

	private WicketTester tester;

	@Before
	public void setUp()
	{
		WicketApplication app = new WicketApplication();
		tester = new WicketTester(app);
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		tester.startPage(Index.class);

		tester.assertRenderedPage(Index.class);
	}

	@Inject
	private Conversation conversation;

	@EJB
	private CheeseDao cheeses;

	@Inject
	private Cart cart;

	@Test
	public void addCheeseToCart()
	{
		Cheese edam = cheeses.byCode("edam");

		assertThat(cart.getItems().isEmpty(), is(true));

		tester.startPage(Index.class);
		tester.assertRenderedPage(Index.class);

		tester.assertModelValue("cheese:1", edam);
		tester.clickLink("cheese:1:add");

		assertThat(cart.getItems().size(), is(1));

		tester.assertModelValue("item:0", edam);

		tester.clickLink("cheese:1:add");

		assertThat(cart.getItems().size(), is(2));
		assertThat(cart.getItems().get(0), is(edam));
		assertThat(cart.getItems().get(1), is(edam));

		tester.clickLink("checkout");
		tester.assertRenderedPage(CheckoutPage.class);
	}
}
