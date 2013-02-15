package com.bananity.controllers;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class BaseController extends HttpServlet {

	protected void sendResponse (HttpServletRequest request, HttpServletResponse response, int httpStatus, int apiStatus, Object data) throws ServletException, IOException {
		response.setStatus(httpStatus);
		request.setAttribute("status", apiStatus);
		request.setAttribute("data", data);

		getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/com/bananity/jspviews/json_view.jsp").forward(request,response);
	}

}