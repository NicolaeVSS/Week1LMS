package com.ss.weeklyone;

import java.io.BufferedReader;
import java.io.*;
import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

//This class encapsulates all Create,Read,Update,Delete (CRUD) for an individual table
public class TableManager
{
	private String filePath;
	private File table = null;
	
	private String tableName;
	private String[] fields; // fields[0] is the primaryKey
	private final ArrayList<Relationship> relationships = new ArrayList<Relationship>();
	
	
	public TableManager(String tableName, String primaryKey, String[] fields) 
	{
		// Building the file header
		fields = ArrayUtils.addAll(new String[] {primaryKey}, fields); 
		String fieldsConverted = Arrays.toString(fields); // gives us bracket encased csv's which we can substring to make our header
		fieldsConverted = fieldsConverted.substring(1, fieldsConverted.length()-1); // removing the brackets
		String header = "#" + fieldsConverted; // header always begins with a # followed by the fields
		
		// the directory our .csv will be stored
		filePath = "./src/dbFiles/" + tableName + ".csv";
		table = new File(filePath);
		
		try 
		{
			if(table.createNewFile()) 
			{
				// if the file doesn't exist and was just created, add our "header"
				System.out.println("File for " + tableName + " successfully created at " + table.getCanonicalPath());
				try 
				{
					FileWriter writer = new FileWriter(table);
					writer.write(header);
					writer.close();
					
					// if the header was successfully written, save our member variables
					this.tableName = tableName;
					this.fields = fields; // TODO should this be a deep copy?
				} 
				catch (IOException e) 
				{
					System.out.println("Something went wrong writing to the file for " + tableName + " at "+ filePath);
					e.printStackTrace();
				}
				
			}
			else 
			{
				// if the file already exists, inform the user and assume the "header" exists and assign variables
				System.out.println("File for " + tableName +" already exists at " + table.getCanonicalPath());
				
				FileReader reader = new FileReader(table);
				BufferedReader buffReader = new BufferedReader(reader);
				
				// Checking the header, assuming the file exists, the header was created properly, and the header hasn't been altered
				String firstLine = buffReader.readLine();
				
				if(firstLine.contains("#")) 
				{
					String[] foundFields = firstLine.substring(1).split(",");
					this.tableName = tableName;
					this.fields = fields;
				}
				else
				{
					System.err.println("Soemthing is wrong with the header of " + tableName + " at " + table.getCanonicalPath());
				}
			}
		} 
		catch (IOException e)
		{
			System.out.println("Something went wrong creating the file for " + tableName + " at "+ filePath);
			e.printStackTrace();
			return;
		}
	}
	
	public String getTableName() 
	{
		return this.tableName;
	}
	
	public String getPrimaryKey() 
	{
		return this.fields[0];
	}
	
	public String[] getFields() 
	{
		return this.fields;
	}
	
	public String getFilePath() 
	{
		return filePath;
	}
	
	public int getFieldIndex(String fieldName) 
	{
		for(int i = 0; i < fields.length; ++i) 
		{
			if(fieldName.equals(fields[i])) 
			{
				return i;
			}
		}
		return -1;
	}
	
	public ArrayList<Relationship> getRelationships() 
	{
		return relationships;
	}
	
	// takes the input array a chops off anything behind the given index 
	// ex spliceStringArray(["0","1","2"], 1) returns ["1","2"] which is a deep copy
	public String[] spliceStringArray(String[] arr, int startInclusive) 
	{
		String[] outArr = new String[arr.length-startInclusive];
		for(int i = startInclusive; i < arr.length; ++i) 
		{
			outArr[i-startInclusive] = arr[i];
		}
		
		return outArr;
	}

	
}
