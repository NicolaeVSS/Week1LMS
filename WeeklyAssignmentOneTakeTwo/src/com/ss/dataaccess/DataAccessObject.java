package com.ss.dataaccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ss.dataobjects.Table;
import com.ss.exception.ImproperDaoNameException;

public abstract class DataAccessObject 
{
	private Table myTable;
	
	public DataAccessObject(Table myTable) 
	{
		this.myTable = myTable;
	}
	
	public static boolean compare(DataAccessObject one, DataAccessObject two) 
	{
		if(one.myTable.getTableName().equals(two.myTable.getTableName()) ) 
		{
			return true;
		}
		return false;
	}
	
	// CREATE
	public void appendToTable(String ...enteredFields) throws ImproperDaoNameException, IOException 
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(myTable.getFilePath(), true));
		String data = "";
		
		for(int i = 0; i < Math.min(enteredFields.length, myTable.getFields().length); ++i) 
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
		BufferedReader reader = new BufferedReader(new FileReader(myTable.getFilePath()));
		String line = reader.readLine();
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		
		while(line != null && !"".equals(line) && !"\n".equals(line)) // TODO do i need the last two checks?
		{
			data.add(new ArrayList<String>(Arrays.asList(line.split(","))));
			line = reader.readLine();
		}
		
		reader.close();
		return data;
		
	}
	
	// UPDATE and DELETE
	public void overwriteTable(ArrayList<ArrayList<String>> data) throws ImproperDaoNameException, IOException 
	{
		// clear the file
		// TODO consider making a backup copy of the file
		myTable.clearTable();
		
		// if the user requested to just empty the table, might as well stop here
		if(data.size() == 0) 
		{
			return;
		}
//		
//		// Chop off column 0, it is the primary key and primary keys are created automatically, no need to re-enter them
//		int innerDimensionSize = 0;
//		
//		for(int i = 0; i < data.size(); ++i) 
//		{
//			innerDimensionSize = data.get(i).size();
//			
//			// if the row isnt empty, remove its first element, the primary key
//			if(!(innerDimensionSize > 0)) 
//			{
//				continue;
//			}
//			else
//			{
//				data.get(i).remove(0); // remove the value at the front
//			}
//		}
//		
//		// now that the primary key is removed, we can being rebuilding the file and regenerating the primary keys
		for(int i = 0; i < data.size(); ++i) 
		{
			appendToTable( data.get(i).toArray(new String[data.get(i).size()]) );
		}
	}

	public String getTableSchema() 
	{
		return myTable.getTableName()+ ":\t" + Arrays.toString(myTable.getFields());
	}
}
