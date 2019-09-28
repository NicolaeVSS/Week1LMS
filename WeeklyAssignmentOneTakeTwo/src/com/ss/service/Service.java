package com.ss.service;

import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.ss.dataaccess.*;
import com.ss.dataobjects.*;
import com.ss.exception.ImproperDaoNameException;

public class Service 
{
	// Service is a singleton
	private static Service instance = null;
	
	private Service() {}
	
	public static Service getInstance() 
	{
		if(instance == null) 
		{
			instance = new Service();
		}
		
		return instance;
	}
	
	// MEMBER VARIABLES
	private DataAccessObject authors = new DataAccessAuthors(new AuthorsTable());
	private DataAccessObject publishers = new DataAccessPublishers(new PublishersTable());
	private DataAccessObject books = new DataAccessBooks(new BooksTable());
	
	public String getTableSchemas() 
	{
		return 	authors.getTableSchema() + "\n"
				+ publishers.getTableSchema() + "\n"
				+ books.getTableSchema() + "\n";
	}

	//CREATE
	//input[0] should be the target table's name
	//input[1] corresponds to the second field (since field[0] is the primary key in any table)
	public void addEntry(String tableName, String ...enteredFields) 
	{
		try 
		{
			DataAccessObject targetDao = identifyDao(tableName);
			
			if(DataAccessObject.compare(targetDao, authors)) 
			{
				targetDao.appendToTable(ArrayUtils.addAll(new String[] {generateUniquePrimaryKey(targetDao)}, enteredFields));
			}
			else if(DataAccessObject.compare(targetDao, publishers))
			{
				targetDao.appendToTable(ArrayUtils.addAll(new String[] {generateUniquePrimaryKey(targetDao)}, enteredFields));
			}
			else if(DataAccessObject.compare(targetDao, books)) 
			{
				if(enteredFields.length < 2) 
				{
					System.out.println("You MUST insert an existing \"authorId,publisherId\" from other tables for your book to be added.\nReturning to main menue.");
					return;
				}

				ArrayList<ArrayList<String>> authorQueryResults = queryTable("Authors", new String[] {enteredFields[0]});
				
				// there must be one entry in Authors that matches the authorId passed in
				// the empty string is not a valid authorId due to query's behavior intentionally matching the empty string to any value
				if(authorQueryResults.size() != 1 || "".equals(enteredFields[0])) 
				{
					System.out.println("Either no authorId " + enteredFields[0] + " was found, or more than one author entry with authorId " + enteredFields[0] + " was found\nReturning to main menue.");
					return;
				}
				
				ArrayList<ArrayList<String>> publisherQueryResults = queryTable("Publishers", new String[] {enteredFields[1]});

				// see comments on above if()
				if(publisherQueryResults.size() != 1 || "".equals(enteredFields[1])) 
				{
					System.out.println("Either no publisherId " + enteredFields[1] + " was found, or more than one publisher entry with publisherId " + enteredFields[1] + " was found\nReturning to main menue.");
					return;
				}
				
				// only add once we've confirmed the user entered two primary keys and they both correspond to exactly one line in publishers and authors
				targetDao.appendToTable(ArrayUtils.addAll(new String[] {generateUniquePrimaryKey(targetDao)}, enteredFields));
			}
		} 
		catch (ImproperDaoNameException e) 
		{
			System.out.println("No operation was done.");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// READ
	// tableName should be the name of the table to be queried 
	// enteredFields corresponds to that tables fields array, and checks if the values are the same in the data unless enteredFeild equals the empty string
	public ArrayList<ArrayList<String>> queryTable(String tableName, String ...enteredFields) 
	{
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		
		try 
		{
			DataAccessObject targetDao = identifyDao(tableName);
			ArrayList<ArrayList<String>> data = targetDao.getTableData();
			
			int innerSize = 0;
			boolean[] rowMatch = new boolean[enteredFields.length];
			
			for(int i = 0; i < data.size(); ++i) 
			{
				Arrays.fill(rowMatch, false);
				innerSize = data.get(i).size();
				
				for(int j = 0; j < Math.min(enteredFields.length,innerSize); ++j) 
				{
					if(data.get(i).get(j).equals(enteredFields[j]) || "".equals(enteredFields[j])) 
					{
						// this if will only check an individual field in a row
						rowMatch[j] = true;
					}
					else if (!"".equals(enteredFields[j]))
					{
						//but since "boolean match" is around, if we encounter value on the row that doesn't match, we set to false and continue
						rowMatch[j] = false;
						continue;
					}
				}
				// if the entire row was a match
				if(!boolArrayContains(rowMatch, false))
				{
					output.add(data.get(i));
				}
			}
		} 
		catch (ImproperDaoNameException e) 
		{
			System.out.println("No operation was done.");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
	
	// see above comments for additional behaviour
	// useComplement indicates whether to do Object.equals(Object o) or !Object.equals(Object o)
	public ArrayList<ArrayList<String>> queryTableComplement(boolean useComplement, String tableName, String ...enteredFields) 
	{
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		
		try 
		{
			DataAccessObject targetDao = identifyDao(tableName);
			ArrayList<ArrayList<String>> data = targetDao.getTableData();
			
			int innerSize = 0;
			boolean[] rowMatch = new boolean[enteredFields.length];
			boolean dataComparisonResult = false; // Decides whether or not to use the complement when comparing field values
			
			for(int i = 0; i < data.size(); ++i) 
			{
				Arrays.fill(rowMatch, false);
				innerSize = data.get(i).size();
				
				for(int j = 0; j < Math.min(enteredFields.length,innerSize); ++j) 
				{
					// if the user sent true, then use the ! operator, otherwise, don't use the ! operator
					dataComparisonResult = useComplement ? !data.get(i).get(j).equals(enteredFields[j]) : data.get(i).get(j).equals(enteredFields[j]);
					System.out.println("Comapring: " + data.get(i).get(j) + " to " + enteredFields[j] + "\nResult: " + dataComparisonResult);
					
					if(dataComparisonResult || "".equals(enteredFields[j])) 
					{
						// this if will only check an individual field in a row
						rowMatch[j] = true;
						System.out.println("This element is either empty or a match");
					}
					else if (!"".equals(enteredFields[j]))
					{
						//but since "boolean match" is around, if we encounter value on the row that doesn't match, we set to false and continue
						rowMatch[j] = false;
						System.out.println("This element was not a match");
						continue;
					}
				}
				// if the entire row was a match aka there is no false entry
				if(!boolArrayContains(rowMatch, false))
				{
					System.out.println("Adding row to output: " + Arrays.toString(data.get(i).toArray()));
					output.add(data.get(i));
				}
				System.out.println();
			}
		} 
		catch (ImproperDaoNameException e) 
		{
			System.out.println("No operation was done.");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
	// UPDATE
	public void updateEntry(String tableName, String ...enteredFields) 
	{
		try 
		{
			DataAccessObject targetDao = identifyDao(tableName);
			ArrayList<ArrayList<String>> targetDaoData = targetDao.getTableData();
			
			int innerSize;
			for(int i = 0; i < targetDaoData.size(); ++i) 
			{
				innerSize = targetDaoData.get(i).size();
				
				// go row by row until the queryResult[0].equals(enteredFields[0]) AKA a primary key match
				if(targetDaoData.get(i).get(0).equals(enteredFields[0])) 
				{
					// then change the values on that row to the values in entered fields, EXCLUDING THE PRIMARY KEY so queryResult[1]'s row  = enteredFields[1+]
					for(int j = 1; j < Math.min(innerSize, enteredFields.length); ++j) 
					{
						targetDaoData.get(i).set(j, enteredFields[j]);
					}
					targetDao.overwriteTable(targetDaoData);
				}
			}
		} 
		catch (ImproperDaoNameException e) 
		{
			System.out.println("No operation was done.");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//DELETE
	// TODO resolve unexpected behaviour, try removing author 2 
	public void removeEntry(String tableName, String ...enteredFields) 
	{
		try 
		{
			DataAccessObject targetDao = identifyDao(tableName); // this'll throw an exception if it cant resolve which DAO to use meaning tableName is safe to use for the rest oft he function 
			ArrayList<ArrayList<String>> queryResult; // acts as a temporary variable to make debugging easier.
			
			if(DataAccessObject.compare(targetDao, authors)) 
			{
				if("".equals(enteredFields[0])) 
				{
					System.out.println("You must enter an authorId to delete an author.\nNo operation was done.");
					return;
				}
				
				System.out.println("BOOKS:");
				// When deleting an author, also delete all the books with matching authorId
				// sending empty string since the first field is the bookId and THEN authorId
				queryResult = queryTableComplement(true, "Books", new String[] {"",enteredFields[0]});
				targetDao.overwriteTable(queryResult);
				
				System.out.println("\nAUTHORS:");
				// Then delete the entry in the Authors table
				queryResult = queryTableComplement(true, tableName, enteredFields[0]);
				targetDao.overwriteTable(queryResult);
			}
			else if(DataAccessObject.compare(targetDao, publishers))
			{
				if("".equals(enteredFields[0])) 
				{
					System.out.println("You must enter a publisherId to delete a publisher.\nNo operation was done.");
					return;
				}
				
				// When deleting a publisher, also delete all the books with matching publisherId
				// sending empty strings since the first field is the bookId and authorId and THEN publisherId
				queryResult = queryTableComplement(true, "Books", new String[] {"","",enteredFields[0]});
				targetDao.overwriteTable(queryResult);
				
				// Then delete the entry in the Publishers table
				queryResult = queryTableComplement(true, tableName, enteredFields[0]);
				targetDao.overwriteTable(queryResult);
			}
			else if(DataAccessObject.compare(targetDao, books)) 
			{
				if("".equals(enteredFields[0])) 
				{
					System.out.println("You must enter a BookId to delete a book.\nNo operation was done.");
					return;
				}

				// When deleting a book,no need to delete the author or publisher
				queryResult = queryTableComplement(true, tableName, new String[] {enteredFields[0]});
				targetDao.overwriteTable(queryResult);
			}
		} 
		catch (ImproperDaoNameException e) 
		{
			System.out.println("No operation was done.");
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	// returns a primary key value that doesnt exist in the current table
	// TODO AUTOMATICALLY 
	private String generateUniquePrimaryKey(DataAccessObject myDao) throws NumberFormatException, IOException
	{
		List<Integer> keys = new ArrayList<Integer>();
		ArrayList<ArrayList<String>> data = myDao.getTableData();
		String line;
		
		// TODO use streams?
		for(int i = 0; i < data.size(); ++i) 
		{
			keys.add(Integer.parseInt(data.get(i).get(0)));
		}
		
		int newKey = 0;
		do 
		{
			newKey++;
		}
		while(keys.contains(newKey) && keys.size() != 0);
		// if the newKey isnt present, stop and return it.
		// if there are no keys present, return the newKey, which will be 1
		
		return String.valueOf(newKey);
	}
	
	private DataAccessObject identifyDao(String tableName) throws ImproperDaoNameException 
	{
		DataAccessObject targetDAO;
		
		switch(tableName) 
		{
		case "Authors":
			targetDAO = authors;
			break;
		case "Publishers":
			targetDAO = publishers;
			break;
		case "Books":
			targetDAO = books;
			break;
		default:
			throw new ImproperDaoNameException();
		}
		
		return targetDAO;
	}
	
	// returns if the boolean arr contains the boolean val passed in 
	private boolean boolArrayContains(boolean[] arr, boolean val)
	{
		for(int i = 0; i < arr.length; ++i) 
		{
			if(arr[i] == val) 
			{
				return true;
			}
		}
		return false;
	}

	public static String make2DArrayListLegible(ArrayList<ArrayList<String>> input) 
	{	
		StringBuilder output = new StringBuilder();

		input.stream()
		.forEach
		(
			row -> output.append(Arrays.toString(row.toArray()) + "\n")
		);
		
		return output.toString();
	}
	
//	
//	private void overwriteTable(Table targetTable, ArrayList<ArrayList<String>> data) 
//	{
//		try 
//		{
//			// clear the file
//			// TODO consider making a backup copy of the file
//			targetTable.clearTable();
//			
//			// if the user requested to empty the table, might as well stop here
//			if(data.size() == 0) 
//			{
//				return;
//			}
//		} 
//		catch (IOException e) 
//		{
//			System.out.println("Error clearing " + targetTable.getTableName() + " for re-writing");
//			e.printStackTrace();
//		}
//		
//		try 
//		{
//			// Chop off column 0, it is the primary key and primary keys are created automatically, no need to re-enter them
//			int innerDimensionSize = 0;
//			
//			for(int i = 0; i < data.size(); ++i) 
//			{
//				innerDimensionSize = data.get(i).size();
//				
//				// if the row isnt empty, remove its first element, the primary key
//				if(!(innerDimensionSize > 0)) 
//				{
//					continue;
//				}
//				else
//				{
//					data.get(i).remove(0); // remove the value at the front
//				}
//			}
//			
//			// now that the primary key is removed, we can being rebuilding the file and regenerating the primary keys
//			for(int i = 0; i < data.size(); ++i) 
//			{
//				targetTable.appendToTable( data.get(i).toArray(new String[data.get(i).size()]) );
//			}
//		} 
//		catch (ImproperTableNameException | IOException e) 
//		{
//			System.out.println("Error writing to " + targetTable.getTableName());
//			e.printStackTrace();
//		}
//	}
//	
}
