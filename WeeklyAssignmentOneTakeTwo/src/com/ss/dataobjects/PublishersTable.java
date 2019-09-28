package com.ss.dataobjects;

import java.io.*;

public class PublishersTable extends Table
{
	public PublishersTable() 
	{
		fields = new String[] {"publisherId", "publisherName","publisherAddr"};
		tableName = "Publishers";
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
