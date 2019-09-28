package com.ss.dataobjects;

import java.io.*;
import java.util.*;


public abstract class Table 
{
	protected String[] fields;
	protected String tableName;
	protected String filePath;
	protected File table;

	public String getFilePath() 
	{
		return filePath;
	}
	public String getTableName() 
	{
		return tableName;
	}
	public String[] getFields() 
	{
		return fields.clone();
	}
	
	public void clearTable() throws IOException 
	{
		if(table.delete())
		{
		    table.createNewFile();
		}
	}
}


