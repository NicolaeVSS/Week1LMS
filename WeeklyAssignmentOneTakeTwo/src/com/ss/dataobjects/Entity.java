package com.ss.dataobjects;

import java.io.*;
import java.util.*;


public abstract class Entity 
{
	protected String[] fieldValues;
	
	public Entity(String ...fieldValues) 
	{
		this.fieldValues = fieldValues.clone(); 
		
	}
	
	public String[] getFields() 
	{
		return fieldValues.clone();
	}
	
//	
//	// TODO implement parent constructor
//	public Entity(String[] fields, String tableName , String filePath) 
//	{
//		this.fields = fields;
//		this.tableName = tableName;
//		this.filePath = filePath;
//		this.table = new File(this.filePath);
//		this.establishFile();
//	}
//	
//	private void establishFile() 
//	{
//		try
//		{	
//			boolean isFileCreated = table.createNewFile();
//			
//			if(isFileCreated) 
//			{
//				System.out.println("File for " + tableName + " successfully created at " + table.getCanonicalPath());
//			}
//			else 
//			{
//				System.out.println("File for " + tableName +" already exists at " + table.getCanonicalPath());
//			}
//		}
//		catch (IOException e)
//		{
//			System.out.println("Something went wrong creating the file for " + tableName + " at "+ this.filePath);
//			e.printStackTrace();
//			return;
//		}
//	}
//	
//	public String getFilePath() 
//	{
//		return filePath;
//	}
//	public String getTableName() 
//	{
//		return tableName;
//	}
//	public String[] getFields() 
//	{
//		return fields.clone();
//	}
//	
//	public void clearTable() throws IOException 
//	{
//		// TODO consider making a backup copy of the file
//		if(table.delete())
//		{
//		    table.createNewFile();
//		}
//	}
}


