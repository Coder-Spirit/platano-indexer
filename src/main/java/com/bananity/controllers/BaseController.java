package com.bananity.controllers;


// Bananity Classes
import com.bananity.caches.CacheBean;

// IO
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Logging
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;


/**
 *  This abstract class encapsules general methods used by all controllers
 *
 *  @author 	Andreu Correa Casablanca
 *  @version 	0.4
 */
public abstract class BaseController extends HttpServlet {

	/**
	 *  Log4j reference
	 */
	protected static Logger log;

	/**
	 *  CacheBean reference
	 */
	@EJB
	protected CacheBean cB;

	/**
	 *  @see javax.servlet.http.HttpServlet#init
	 *  @see javax.servlet.ServletConfig
	 */
	@Override
		public void init(ServletConfig config) throws ServletException {
			super.init(config);
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
			log = Logger.getLogger(this.getClass());
		}

	/**
	 *  This method sends a json response to the http client
	 *
	 *  @param request 		Reference to request data
	 *  @param response 	Reference to response data
	 *  @param httpStatus 	http status to be returned (to the http client)
	 *  @param apiStatus 	api status to be returned (to the api consumer)
	 *  @param data 		response data
	 */
	protected void sendResponse (HttpServletRequest request, HttpServletResponse response, int httpStatus, int apiStatus, Object data) throws ServletException, IOException {
		response.setStatus(httpStatus);
		request.setAttribute("status", apiStatus);
		request.setAttribute("data", data);

		getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/com/bananity/jspviews/json_view.jsp").forward(request,response);
	}

}