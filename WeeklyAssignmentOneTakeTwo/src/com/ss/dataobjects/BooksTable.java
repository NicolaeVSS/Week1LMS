package com.ss.dataobjects;

public class BooksTable extends Table
{
	public BooksTable() 
	{
		super(new String[] {"bookId","authorId","publisherId","bookTitle"},"Books","./dbFiles/Books.csv");
	}
	
	public BooksTable(String filePath) 
	{
		super(new String[] {"bookId","authorId","publisherId","bookTitle"},"Books",filePath);
	}
}
