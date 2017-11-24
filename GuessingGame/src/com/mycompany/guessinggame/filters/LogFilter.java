package com.mycompany.guessinggame.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.guessinggame.servlets.GameServlet;

/**
 * Servlet Filter implementation class LogFilter
 */
@WebFilter("/winners")
public class LogFilter implements Filter {
	
	// configure logging
	private static final Logger logger = LogManager.getLogger( GameServlet.class );
		
    /**
     * Default constructor. 
     */
    public LogFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO 
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		String log = "\nrequestURL: " + req.getRequestURL().toString()
				+ "\nrequestURI: " + req.getRequestURI().toString()
				+ "\ncontexPath: " + req.getContextPath()
				+ "\nservletPath: " + req.getServletPath() 
				+ "\n\nServer info: "
				+ "\nserverName: " + req.getServerName() 
				+ "\nsreverPort: " + req.getServerPort() 
				+ "\n\nClient info: "
				+ "\nremoteAddress: " + req.getRemoteAddr() 
				+ "\nremoteHost: " + req.getRemoteHost() 
				+ "\nremotePort: " + req.getRemotePort()
				+ "\nremoteUser: " + req.getRemoteUser()
				+"\n\nHeaders: ";
		
		Enumeration<String> headers = req.getHeaderNames();
		while ( headers.hasMoreElements() ) {
			String header = headers.nextElement();
			log += "\n" + header + ": " + req.getHeader( header );
		}
		
		log += "\n\nServlet Contex info: " 
				+ "\nrealPath: " + req.getServletContext().getRealPath( "" );
		
		// logging
		logger.info( log );

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO 
	}

}
