package com.cheesr.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.transaction.Transactional;

import org.apache.wicket.protocol.http.WicketFilter;

@WebFilter(filterName = "Cheesr", value = "/*", initParams = {
		@WebInitParam(name = "applicationClassName", value = "com.cheesr.web.WicketApplication"),
		@WebInitParam(name = "filterMappingUrlPattern", value = "/*") })
public class CheesrFilter extends WicketFilter {
	@Transactional
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		super.doFilter(request, response, chain);
	}
}
