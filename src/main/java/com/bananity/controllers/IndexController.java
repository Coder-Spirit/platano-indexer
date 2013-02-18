package com.bananity.controllers;


// Bananity Classes
import com.bananity.models.IndexModelBean;
import com.bananity.nlp.AnalyzerModule;
import com.bananity.util.SearchesTokenizer;
import com.bananity.util.HashBag;
import com.bananity.util.ResultItemComparator;

// Cache
import com.google.common.cache.Cache;

// Java utils
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/index")
public class IndexController extends BaseController {

	@EJB
	private IndexModelBean imB;

	@Override
		public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				String 	method 		= request.getParameter("m");
				String 	collName 	= request.getParameter("c");
				String 	item 		= request.getParameter("item");

				if (collName == null || collName.length() == 0 || item == null || item.length() == 0) {
					throw new Exception( "Invalid parameters" );
				}

				if (method.equals("insert")) {
					insertLogic(collName, item);
				} else if (method.equals("remove")) {

				} else {
					throw new Exception("Inexistent method : m="+method);
				}

				sendResponse(request, response, HttpServletResponse.SC_OK, 0, null);

			} catch (Exception e) {
				log.warn("BAD_REQUEST from "+request.getRemoteAddr()+" with exception "+e.getMessage()+", cause: "+e.getCause()+", params: "+request.getQueryString());
				e.printStackTrace();

				sendResponse(request, response, HttpServletResponse.SC_BAD_REQUEST, 1, null);
			}
		}

		private void insertLogic (String collName, String item) throws Exception {
			imB.insert(collName, item, SearchesTokenizer.getSubTokensList(item));

			addToCache(collName, item);
		}

		private void  addToCache (String collName, String item) throws Exception {
			Cache<String, ArrayList<String>> cache = cB.getResultCache(collName);
			
			if (cache == null) {
				throw new Exception("¡Cache not foud for collection \""+collName+"\"!");
			}

			String cacheKeyBase = AnalyzerModule.normalize( item );

			ArrayList<String> expandedCacheKeyBase = AnalyzerModule.getAllSubstrings(cacheKeyBase, 2);

			for (String cacheKeyBaseItem : expandedCacheKeyBase) {
				addToCacheToken (cache, item, cacheKeyBaseItem, 5);
				addToCacheToken (cache, item, cacheKeyBaseItem, 10);
				addToCacheToken (cache, item, cacheKeyBaseItem, 15);
				addToCacheToken (cache, item, cacheKeyBaseItem, 20);
			}
		}

		private void addToCacheToken (Cache<String, ArrayList<String>> cache, String item, String cacheKeyBaseItem, Integer limit) throws Exception {
			String cacheKey = new StringBuilder(cacheKeyBaseItem).append("@").append(limit.toString()).toString();
			ArrayList<String>  cachedResult = cache.getIfPresent(cacheKey);

			if (cachedResult == null) return;

			cachedResult.add(item);

			Collections.sort(cachedResult, new ResultItemComparator(SearchesTokenizer.getSubTokensBag(cacheKeyBaseItem)));

			if (cachedResult.size() > 10) {
				cachedResult = new ArrayList<String>(cachedResult.subList(0, limit));
			}

			cache.put(cacheKey, cachedResult);
		}
}