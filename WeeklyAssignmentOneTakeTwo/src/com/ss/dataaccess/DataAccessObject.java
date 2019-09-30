package com.ss.dataaccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.ss.dataobjects.Entity;
import com.ss.exception.ImproperDaoNameException;

public abstract class DataAccessObject 
{
	protected Entity myTable;
	protected String[] fields;
	protected String tableName;
	protected String filePath;
	protected File table;
	
	
	public DataAccessObject(String[] fields, String tableName , String filePath) 
	{
		this.fields = fields;
		this.tableName = tableName;
		this.filePath = filePath;
		this.table = new File(this.filePath);
		
		try
		{	
			boolean isFileCreated = table.createNewFile();
			
			if(isFileCreated) 
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

	// CREATE
	public void appendToTable(String ...enteredFields) throws ImproperDaoNameException, IOException 
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true));
		String data = "";
		
		for(int i = 0; i < Math.min(enteredFields.length, this.fields.length); ++i) 
		{
			data += enteredFields[i] + ",";
		}
		
		// remove the last ","
		data = data.substring(0, data.length()-1);
		
		writer.write(data);
		writer.newLine();
		writer.close();
	}
	
	
	// READ
	public ArrayList<ArrayList<String>> getTableData() throws IOException
	{
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		
		BufferedReader reader = new BufferedReader(new FileReader(this.filePath));
		
		// for each line of the file, use split(",") and cast to an ArrayList 
		// AND THEN add that split/casted row to the data ArrayList to be returned
		reader.lines().forEach(row -> 
		{
			data.add(new ArrayList<String>(Arrays.asList(row.split(","))));
		});
		
		reader.close();
		return data;
	}
	
	// UPDATE and DELETE
	public void overwriteTable(ArrayList<ArrayList<String>> data) throws ImproperDaoNameException, IOException 
	{
		// clear the file
		clearTable();
		
		// if the user requested to just empty the table, might as well stop here
		if(data.size() == 0) 
		{
			return;
		}

		for(int i = 0; i < data.size(); ++i) 
		{
			appendToTable( data.get(i).toArray(new String[data.get(i).size()]) );
		}
	}

	public void clearTable() throws IOException 
	{
		// TODO consider making a backup copy of the file
		if(table.delete())
		{
		    table.createNewFile();
		}
	}
	
	public String getTableName() 
	{
		return tableName;
	}
	
	public String getTableSchema() 
	{
		return tableName + ":\t" + Arrays.toString(fields);
	}
}
