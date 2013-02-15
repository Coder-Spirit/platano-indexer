package com.bananity.controllers;

// Bananity Classes
import com.bananity.caches.CacheBean;
import com.bananity.models.IndexModelBean;
import com.bananity.nlp.AnalyzerModule;
import com.bananity.util.CandidatesCache;
import com.bananity.util.ResultItemComparator;
import com.bananity.util.SearchSubstrings;
import com.bananity.util.Jaccard;

// Cache
import com.google.common.cache.Cache;

// Java utils
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;


@WebServlet("/search")
public class SearchController extends BaseController {

	private static Logger log;

	@EJB
	private IndexModelBean imB;

	@EJB
	private CacheBean cB;

	@Override
		public void init(ServletConfig config) throws ServletException {
			super.init(config);
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
			log = Logger.getLogger(SearchController.class);
		}

	@Override
		public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				String 	collName 	= request.getParameter("c");
				String 	searchTerm 	= request.getParameter("searchTerm");
				int 	limit 		= Integer.parseInt(request.getParameter("limit"));

				if (collName == null || collName.length() == 0 || searchTerm == null || limit <= 0) {
					throw new Exception( "Invalid parameters" );
				}

				ArrayList<String> searchResult = searchLogic(collName, searchTerm, limit);

				sendResponse(request, response, HttpServletResponse.SC_OK, 0, searchResult);

			} catch (Exception e) {
				log.warn("BAD_REQUEST from "+request.getRemoteAddr()+" with exception "+e.getMessage()+", cause: "+e.getCause()+", params: "+request.getQueryString()+", body: "+request.getReader().readLine());
				e.printStackTrace();
				
				sendResponse(request, response, HttpServletResponse.SC_BAD_REQUEST, 1, null);
			}
		}

		private ArrayList<String> searchLogic (String collName, String searchTerm, int limit) throws Exception {
			Cache<String, ArrayList<String>> cache = cB.getResultCache(collName);
			
			if (cache == null) {
				throw new Exception("¡Cache not foud for collection \""+collName+"\"!");
			}

			String cacheKey	= AnalyzerModule.normalize( searchTerm ) + "@" + limit;
			ArrayList<String> finalResult = cache.getIfPresent( cacheKey );
			if (finalResult != null) {
				return finalResult;
			}

			SearchSubstrings searchSubstrings = new SearchSubstrings(searchTerm);
			CandidatesCache<String> candidates = new CandidatesCache<String>(new ResultItemComparator(searchSubstrings.getHashBag()), limit);

			int lengthThreshold = searchSubstrings.getMaxTokenLength();

			ArrayList<String> partialResults;

			Set<String> usedTokens = new HashSet();
			ArrayList<String> currSearch, tmpSearch;

			while ( candidates.size() < limit && lengthThreshold > 0 ) {
				tmpSearch = new ArrayList<String>( searchSubstrings.getUniqByLength( lengthThreshold ) );
				currSearch = new ArrayList<String>();

				for ( String s : tmpSearch ) {
					if ( ! usedTokens.contains( s ) ) {
						usedTokens.add( s );
						currSearch.add( s );
					}
				}

				if ( currSearch.size() > 0 && (partialResults = imB.find(collName, currSearch, limit)) != null ) {
					for ( String partialResult : partialResults ) {
						candidates.put (partialResult);
					}
				}

				--lengthThreshold;
			}

			finalResult = candidates.getRecords();
			cache.put( cacheKey, finalResult );

			return finalResult;
		}
}
