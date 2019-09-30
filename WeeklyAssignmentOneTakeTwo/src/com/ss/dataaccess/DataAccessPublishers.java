package com.ss.dataaccess;

import com.ss.dataobjects.Entity;

public class DataAccessPublishers extends DataAccessObject
{
	public DataAccessPublishers() 
	{
		super(new String[] {"publisherId", "publisherName","publisherAddr"},"Publishers","./dbFiles/Publishers.csv");
	}
	
	public DataAccessPublishers(String filePath) 
	{
		super(new String[] {"publisherId", "publisherName","publisherAddr"},"Publishers",filePath);
	}
}
