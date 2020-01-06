package com.ss.dataaccess;

import com.ss.dataobjects.Entity;

public class DataAccessAuthors extends DataAccessObject
{
	public DataAccessAuthors() 
	{
		super(new String[] {"authorId", "authorName"},"Authors","./dbFiles/Authors.csv");
	}
	
	public DataAccessAuthors(String filePath) 
	{
		super(new String[] {"authorId", "authorName"},"Authors",filePath);
	}
}
