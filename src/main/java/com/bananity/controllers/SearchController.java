package com.bananity.controllers;

// Bananity Classes
import com.bananity.models.IndexModelBean;
import com.bananity.text.TextNormalizer;
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

// IO
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *  This webservlet allows to search items into the index collections
 *
 *  @author 	Andreu Correa Casablanca
 *  @version 	0.4
 */
@WebServlet("/search")
public class SearchController extends BaseController {

	/**
	 *  IndexModelBean reference
	 */
	@EJB
	private IndexModelBean imB;

	/**
	 *  Handles Post requests
	 *
	 *  @see javax.servlet.http.HttpServlet#doPost
	 *  @see com.bananity.controllers.SearchController#searchLogic
	 */
	@Override
		public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				String 	collName 	= request.getParameter("c");
				String 	searchTerm 	= TextNormalizer.normalizeText(request.getParameter("searchTerm"), true);
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

	/**
	 *  The magic happens here
	 *
	 *  @param collName 	Index collection name
	 *  @param searchTerm 	Search text
	 *  @param limit 		Maximum number of items that result can have
	 *
	 *  @return 			List of found elements in the specified collection searching by searchTerm
	 */
		private ArrayList<String> searchLogic (String collName, String searchTerm, int limit) throws Exception {
			Cache<String, ArrayList<String>> cache = cB.getResultCache(collName);
			
			if (cache == null) {
				throw new Exception("¡Cache not foud for collection \""+collName+"\"!");
			}

			String cacheKey	= new StringBuilder(TextNormalizer.flattenText( searchTerm )).append('@').append(limit).toString();
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
