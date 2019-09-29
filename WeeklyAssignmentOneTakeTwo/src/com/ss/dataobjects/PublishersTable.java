package com.ss.dataobjects;

public class PublishersTable extends Table
{
	public PublishersTable() 
	{
		super(new String[] {"publisherId", "publisherName","publisherAddr"},"Publishers","./dbFiles/Publishers.csv");
	}
	
	public PublishersTable(String filePath) 
	{
		super(new String[] {"publisherId", "publisherName","publisherAddr"},"Publishers",filePath);
	}
}
