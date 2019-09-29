package com.ss.dataaccess.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ss.dataaccess.*;
import com.ss.dataobjects.*;
import com.ss.exception.ImproperDaoNameException;

class DataAccessObjectTest 
{
	@BeforeAll
	static void setUpBeforeClass() throws Exception {}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
		
	}

	@Test 
	final void testAppendToTableAuthorDao() throws ImproperDaoNameException, IOException 
	{
		DataAccessObject myDao = new DataAccessAuthors(new AuthorsTable("./dbFilesTest/Authors.csv"));
		String[] authorData = new String[] {"1","testAppendToTableAuthorDaoAuthorName"};
		ArrayList<ArrayList<String>> result;

		myDao.appendToTable(authorData);
		result = myDao.getTableData();
		
		for(int i = 0; i < result.size(); ++i) 
		{
			if(result.get(i).size() != authorData.length) 
			{
				continue;
			}
			
			for(int j = 0; j < authorData.length; ++j) 
			{
				if (Arrays.equals(result.get(i).toArray(), authorData)) 
				{
					// success!
					return;
				}
			}
		}

	}
	
	@Test
	final void testOverwriteTable() throws IOException, ImproperDaoNameException 
	{
		DataAccessObject myDao = new DataAccessAuthors(new AuthorsTable("./dbFilesTest/Authors.csv"));
		ArrayList<ArrayList<String>> previousData = myDao.getTableData();

		// create an arraylist containing what we want to overwrite the file with
		ArrayList<ArrayList<String>> overwriteData = new ArrayList<ArrayList<String>>();

		myDao.overwriteTable(overwriteData);
		
		// get all the data in the file after the overwrite and compare
		ArrayList<ArrayList<String>> newData = myDao.getTableData();
		
		
		if(overwriteData.equals(newData)) 
		{
			// success!
			myDao.overwriteTable(previousData);
			return;
		}
		// else:
		fail("Overwritten data didnt match what was sent");
	}
}
