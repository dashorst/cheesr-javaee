package com.cheesr.web;

import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.cdi.ConversationPropagation;
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

		CdiConfiguration cdiConfiguration = new CdiConfiguration();
		cdiConfiguration.setPropagation(ConversationPropagation.ALL);
		cdiConfiguration.configure(this);

		getComponentInstantiationListeners().add(
				new JavaEEComponentInjector(this,
						new WildflyWicketJndiNamingStrategy()));

		mountPage("/checkout", CheckoutPage.class);
		mountPage("/orders", OrdersPage.class);
	}

	public static class WildflyWicketJndiNamingStrategy implements
			IJndiNamingStrategy {
		private static final long serialVersionUID = 1L;

		@Override
		public String calculateName(String ejbName, Class<?> ejbType) {
			return "java:comp/"
					+ (ejbName == null ? ejbType.getSimpleName() : ejbName);
		}
	}
}
