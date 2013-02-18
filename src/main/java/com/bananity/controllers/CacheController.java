package com.bananity.controllers;


// Bananity Classes
import com.bananity.caches.CacheBean;

// Caches
import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;

// Java utils
import java.util.HashMap;

// IO
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *  This webservlet allows admins to view the caches status
 *
 *  @author Andreu Correa Casablanca
 *  @version 0.4
 */
@WebServlet("/cache")
public class CacheController extends BaseController {

	@EJB
	private CacheBean cB;

	@Override
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				
				String collName 	= request.getParameter("c");
				String cacheType 	= request.getParameter("cacheType");

				if (cacheType == null || (!cacheType.equals("results") && !cacheType.equals("tokens")) || collName == null) {
					throw new Exception("Invalid parameters");
				}

				HashMap<String, Object> data = new HashMap<String, Object>();
				Cache cache;
				CacheStats stats = null;

				if (cacheType.equals("results")) {	
					cache = cB.getResultCache(collName);
				} else if (cacheType.equals("tokens")) {
					cache = cB.getTokensCache(collName);
				} else {
					throw new Exception("Not valid cacheType : "+cacheType);
				}

				if (cache == null) {
					throw new Exception("The collection \""+collName+"\" doesn't exist.");
				}
				data.put("size", cache.size());
				stats = cache.stats();

				data.put("averageLoadPenalty", stats.averageLoadPenalty());
				data.put("evictionCount", stats.evictionCount());
				data.put("hitCount", stats.hitCount());
				data.put("hitRate", stats.hitRate());
				data.put("loadCount", stats.loadCount());
				data.put("loadExceptionCount", stats.loadExceptionCount());
				data.put("loadExceptionRate", stats.loadExceptionRate());
				data.put("loadSuccessCount", stats.loadSuccessCount());
				data.put("missCount", stats.missCount());
				data.put("missRate", stats.missRate());
				data.put("requestCount", stats.requestCount());
				data.put("totalLoadTime", stats.totalLoadTime());

				sendResponse(request, response, HttpServletResponse.SC_OK, 0, data);

			} catch (Exception e) {
				log.warn("BAD_REQUEST from "+request.getRemoteAddr()+" with exception "+e.getMessage()+", cause: "+e.getCause()+", params: "+request.getQueryString());
				e.printStackTrace();

				sendResponse(request, response, HttpServletResponse.SC_BAD_REQUEST, 1, null);
			}
		}
}
