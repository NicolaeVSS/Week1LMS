package com.ss.dataaccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		
		BufferedReader reader = new BufferedReader(new FileReader(myTable.getFilePath()));
		
		// for each line of the file, add it to a 1D ArrayList
		// then, for each row in the above ArrayList, use split(",") and cast to an ArrayList 
		// AND THEN add that split/casted row to the data ArrayList to be returned
		reader.lines().collect(Collectors.toList()).forEach(ele -> 
		{
			data.add(new ArrayList<String>(Arrays.asList(ele.split(","))));
		});
		
		reader.close();
		return data;
	}
	
	// UPDATE and DELETE
	public void overwriteTable(ArrayList<ArrayList<String>> data) throws ImproperDaoNameException, IOException 
	{
		// clear the file
		myTable.clearTable();
		
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

	public String getTableName() 
	{
		return myTable.getTableName();
	}
	
	public String getTableSchema() 
	{
		return myTable.getTableName()+ ":\t" + Arrays.toString(myTable.getFields());
	}
}
