package com.bananity.storages;


import java.util.ArrayList;
import java.util.Collection;


public interface IIndexStorage
{
	public ArrayList<String> findSubToken (String collName, String subToken) throws Exception;
	public void insert (String collName, String subToken, Collection<String> items) throws Exception;
}