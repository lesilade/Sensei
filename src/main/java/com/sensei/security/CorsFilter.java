package com.sensei.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter {

	 @Override
	    public void init(FilterConfig filterConfig) throws ServletException {

	    }

	    @Override
	    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
	        HttpServletResponse response = (HttpServletResponse) servletResponse;
	        HttpServletRequest request= (HttpServletRequest) servletRequest;

	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
	        response.setHeader("Access-Control-Allow-Headers", "*");
	        //response.setsetHeader("Access-Control-Allow-Credentials", true);
	        response.setIntHeader("Access-Control-Max-Age", 180);
	        filterChain.doFilter(servletRequest, servletResponse);
	    }

	    @Override
	    public void destroy() {

	    }

/*		@Override
		public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
				throws IOException, ServletException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void init(FilterConfig arg0) throws ServletException {
			// TODO Auto-generated method stub
			
		}*/
}
