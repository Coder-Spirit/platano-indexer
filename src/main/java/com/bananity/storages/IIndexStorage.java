package com.bananity.storages;


import com.bananity.util.SearchTerm;

import java.util.ArrayList;
import java.util.Collection;


/**
 *  Interface that all storages used in the search engine must implement
 *
 *  @author 	Andreu Correa Casablanca
 *  @version 	0.4
 */
public interface IIndexStorage
{
	/**
	 *  This method finds strings that have 'subToken' as a substring in the specified storage collection (collName)
	 *
	 *  @param collName 	Name of the collection where the search will be done
	 *  @param subToken 	subToken used to search into the storage (we can understand the storage as a key-value storage, where the subtoken is a key)
	 *
	 *  @return 			List of strings stored that contains as a substring the specified 'subToken'
	 */
	public ArrayList<SearchTerm> findSubToken (String collName, String subToken) throws Exception;

	/**
	 *  This method inserts a list of items in the specified storage collection (collName) using 'subToken' as a key
	 *
	 *  @param collName 	Name of the collection where the insertion will be done
	 *  @param subToken 	subToken to be created/updated
	 *  @param items 		items to be inserted using 'subToken' as a key
	 */
	public void insert (String collName, String subToken, Collection<SearchTerm> items) throws Exception;
}