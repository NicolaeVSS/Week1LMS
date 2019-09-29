package com.ss.dataaccess;

import java.util.Arrays;

import com.ss.dataobjects.Table;

public class DataAccessBooks extends DataAccessObject
{

	public DataAccessBooks(Table myTable) {
		super(myTable);
	}
	
	@Override
	public String getTableSchema() 
	{
		return myTable.getTableName()+ ":\t\t" + Arrays.toString(myTable.getFields());
	}
}
