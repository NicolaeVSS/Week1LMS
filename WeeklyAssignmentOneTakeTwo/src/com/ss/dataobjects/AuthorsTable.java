package com.ss.dataobjects;

public class AuthorsTable extends Table
{	
	public AuthorsTable() 
	{
		super(new String[] {"authorId", "authorName"},"Authors","./dbFiles/Authors.csv");
	}
	
	public AuthorsTable(String filePath) 
	{
		super(new String[] {"authorId", "authorName"},"Authors",filePath);
	}
}
