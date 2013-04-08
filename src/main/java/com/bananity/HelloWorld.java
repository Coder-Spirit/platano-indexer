package com.bananity;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

@WebServlet("/hw")
public class HelloWorld extends HttpServlet {

	private static Logger log;

	@Override
		public void init(ServletConfig config) throws ServletException {
			super.init(config);
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
			log = Logger.getLogger("HelloWorld");
		}

	@Override
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				response.getWriter().println("Hello World !!");
				log.info("Hello World !!");
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println("Exception: "+e.getMessage());
			}
		}
}
