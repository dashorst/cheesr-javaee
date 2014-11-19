package com.cheesr.web;

import org.apache.deltaspike.core.api.provider.BeanManagerProvider;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.cdi.ConversationPropagation;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.javaee.injection.JavaEEComponentInjector;
import org.wicketstuff.javaee.naming.IJndiNamingStrategy;

public class WicketApplication extends WebApplication {
	@Override
	public Class<? extends WebPage> getHomePage() {
		return Index.class;
	}

	@Override
	public void init() {
		super.init();

		BeanValidationConfiguration beanValidation = new BeanValidationConfiguration();
		beanValidation.configure(this);

		CdiConfiguration cdiConfiguration = new CdiConfiguration(BeanManagerProvider.getInstance().getBeanManager());
		cdiConfiguration.setPropagation(ConversationPropagation.ALL);
		cdiConfiguration.configure(this);

		mountPage("/checkout", CheckoutPage.class);
		mountPage("/orders", OrdersPage.class);
	}

}
