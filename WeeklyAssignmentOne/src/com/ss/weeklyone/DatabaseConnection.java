package com.ss.weeklyone;

import java.util.*;
import org.apache.commons.lang3.ArrayUtils;

public class DatabaseConnection 
{
	// DatabaseConnection is a singleton
	private static DatabaseConnection instance = null;
	
	private DatabaseConnection() {}
	
	public static DatabaseConnection getInstance() 
	{
		if(instance == null) 
		{
			instance = new DatabaseConnection();
		}
		
		return instance;
	}

	private ArrayList<TableManager> tables = new ArrayList<TableManager>();
	
	public ArrayList<TableManager> getTablesRef()
	{
		return this.tables;
	}
	
	public String[] getAllTableNames()
	{
		String[] out = new String[tables.size()];
		
		for(int i = 0; i < tables.size(); ++i) 
		{
			out[i] = tables.get(i).getTableName();
		}
		
		return out;
	}
	
	public String[] getTableFields(String tableName) 
	{
		return tables.get(getTableIndex(tableName)).getFields();
	}
	
	public int getTableIndex(String tableName) 
	{
		for(int i = 0; i < tables.size(); ++i) 
		{
			if(tableName.equals(tables.get(i).getTableName())) 
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public void createTable(String tableName, String primaryKey, String ...fields) 
	{
		// TODO maybe throw an exception or return false instead of just printing
		if(getTableIndex(tableName) != -1) 
		{
			System.out.println("Table " + tableName + " already exists and thus will not be made again.");
			return;
		}
		
		tables.add(new TableManager(tableName, primaryKey, fields));
	}
	
	public boolean addRelationship(int parentTableIndex, int targetTableIndex, int parentFieldIndex, int targetFieldIndex, RelationType relation) 
	{
		tables.get(targetTableIndex).getRelationships().add(new Relationship(parentTableIndex, targetTableIndex, parentFieldIndex, targetFieldIndex, relation));
		return false;
	}
	
	
	// Read operation from CRUD
	// SELECT col1 FROM table1 WHERE condition;
	public String[] queryTable(String[] fields, String tableName, String left, String op, String right) 
	{
		int tableIndex = -1;
		for(int i = 0; i < tables.size(); ++i) 
		{
			if(tableName.equals(tables.get(i).getTableName())) 
			{
				tableIndex = i;
			}
		}
		
		
		if(tableName == "" || tableName == null || tableIndex == -1 ) 
		{
			System.out.println("Invalid table name. Exiting query, returning null");
			return null;
		}
		
		switch(op) 
		{
		case "==":
			
			break;
		case "":
			break;
		}
		
		String[] result = null;
		
		return result;
	}
}
