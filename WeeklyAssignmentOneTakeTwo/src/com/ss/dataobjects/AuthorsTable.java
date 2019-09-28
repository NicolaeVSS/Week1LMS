package com.ss.dataobjects;

import java.io.*;

public class AuthorsTable extends Table
{
	public AuthorsTable() 
	{
		fields = new String[] {"authorId", "authorName"};
		tableName = "Authors";
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
