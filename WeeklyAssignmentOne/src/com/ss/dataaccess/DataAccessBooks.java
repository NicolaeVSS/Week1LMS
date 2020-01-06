package com.ss.dataaccess;

import java.util.Arrays;

import com.ss.dataobjects.Entity;

public class DataAccessBooks extends DataAccessObject
{
	public DataAccessBooks() 
	{
		super(new String[] {"bookId","authorId","publisherId","bookTitle"},"Books","./dbFiles/Books.csv");
	}
	
	public DataAccessBooks(String filePath) 
	{
		super(new String[] {"bookId","authorId","publisherId","bookTitle"},"Books",filePath);
	}
	
//	@Override
//	public String getTableSchema() 
//	{
//		return myTable.getTableName()+ ":\t\t" + Arrays.toString(myTable.getFields());
//	}
}
