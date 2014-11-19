package com.cheesr.web;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.wicket.protocol.http.WicketFilter;

@WebFilter(filterName = "Cheesr", value = "/*", initParams = {
        @WebInitParam(name = "applicationClassName", value = "com.cheesr.web.WicketApplication"),
        @WebInitParam(name = "filterMappingUrlPattern", value = "/*") })
public class CheesrFilter extends WicketFilter {

    @Resource
    private UserTransaction ut;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            ut.begin();

            super.doFilter(request, response, chain);

            if (ut.getStatus() == Status.STATUS_ACTIVE) {
                ut.commit();
            }
        }
        catch (Exception e) {
            try {
                ut.rollback();
            }
            catch (SystemException e1) {
                throw new ServletException(e);
            }
        }

    }
}
