package com.cheesr.web;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import java.io.File;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import org.apache.wicket.util.tester.WicketTester;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cheesr.dao.CheeseDao;
import com.cheesr.domain.Cart;
import com.cheesr.entities.Cheese;

@RunWith(Arquillian.class)
public class TestIndex
{
	@Deployment
	public static WebArchive deployment()
	{
		File[] dependencies =
			Maven.configureResolver().workOffline().loadPomFromFile("pom.xml")
				.importCompileAndRuntimeDependencies()
				// .addDependencies()
				.resolve().withTransitivity().as(File.class);

		WebArchive war =
			ShrinkWrap.create(WebArchive.class).addAsResource(new File("target/classes"), "")
				.addAsLibraries(dependencies)
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/cheesr-ds.xml"));
		System.out.println(war.toString(true));
		return war;
	}

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
