package com.bananity.controllers;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *  This abstract class encapsules general methods used by all controllers
 *
 *  @author Andreu Correa Casablanca
 *  @version 0.3
 */
public abstract class BaseController extends HttpServlet {

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