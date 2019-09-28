package com.ss.dataobjects;

import java.io.*;
import java.util.Arrays;

public class BooksTable extends Table
{
	public BooksTable() 
	{
		fields = new String[] {"bookId","authorId","publisherId","bookTitle"};
		tableName = "Books";
		filePath = "./dbFiles/" + tableName + ".csv";
		table = new File(this.filePath);
		
		try 
		{	
			if(table.createNewFile()) 
			{
				System.out.println("File for " + tableName + " successfully created at " + table.getCanonicalPath());
			}
			else 
			{
				System.out.println("File for " + tableName +" already exists at " + table.getCanonicalPath());
			}
		} 
		catch (IOException e)
		{
			System.out.println("Something went wrong creating the file for " + tableName + " at "+ this.filePath);
			e.printStackTrace();
			return;
		}
	}
}
