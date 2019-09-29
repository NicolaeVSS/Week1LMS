package com.ss.service.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ss.service.Service;

class ServiceTest 
{
	@BeforeAll
	static void setUpBeforeClass() throws Exception {}

	@AfterAll
	static void tearDownAfterClass() throws Exception {}

	// CREATE AND DELETE
	@Test
	final void testAddRemoveEntry() {
		Service myService = Service.getInstance();
		
		// TEST AUTHORS
		String tableName = "Authors";
		String[] enteredFields = new String[] {"testAddEntryAuthorName"}; // must have one+ string for the test to work
		myService.addEntry(tableName, enteredFields);
		
		// since add entry automatically creates a pk value, we need to not query by pk, and query by authorName
		ArrayList<ArrayList<String>> myAuthorsData = myService.queryTable(tableName, new String[] {"", enteredFields[0]});
		
		if(myAuthorsData.size() != 1) 
		{
			fail("None or more than one rows was returned from Authors matching: " + enteredFields[0]);
		}
		
		// TEST PUBLISHERS
		tableName = "Publishers";
		enteredFields = new String[] {"testAddEntryPublisherName", "testAddEntryPublisherAddress"}; // must have two+ strings for the test to work
		myService.addEntry(tableName, enteredFields);
		
		ArrayList<ArrayList<String>> myPublishersData = myService.queryTable(tableName, new String[] {"", enteredFields[0], enteredFields[1]});
		
		if(myPublishersData.size() != 1) 
		{
			fail("None or more than one rows was returned from Publishers matching: " + enteredFields[0] + "," + enteredFields[1]);
		}
		
		//TEST BOOKS
		tableName = "Books";
		enteredFields = new String[] {myAuthorsData.get(0).get(0), myPublishersData.get(0).get(0), "testAddEntryBookTitle"}; // must have three+ strings for the test to work
		myService.addEntry(tableName, enteredFields);
		
		ArrayList<ArrayList<String>> myBooksData = myService.queryTable(tableName, new String[] {"", enteredFields[0], enteredFields[1], enteredFields[2]});
		
		if(myPublishersData.size() != 1) 
		{
			fail("None or more than one rows was returned from Books matching: " + enteredFields[0] + "," + enteredFields[1] + "," + enteredFields[2]);
		}
		
		// TEST removeEntry() on a publisher to remove their books
		myService.removeEntry("Publishers", myBooksData.get(0).get(2));
		
		// did it remove the book(s)?
		ArrayList<ArrayList<String>> myRemovedBooksData = myService.queryTable("Books", myBooksData.get(0).get(0));
		if(myRemovedBooksData.size() != 0) 
		{
			fail("Didnt remove Book " + myBooksData.get(0).get(0));
		}
		
		//did it remove the publisher?
		ArrayList<ArrayList<String>> myRemovedPublishersData = myService.queryTable("Publishers", myBooksData.get(0).get(2));
		if(myRemovedPublishersData.size() != 0) 
		{
			fail("Didnt remove Publisher " + myBooksData.get(0).get(2));
		}
		
		// remove the author as cleanup
		myService.removeEntry("Authors", myBooksData.get(0).get(1));
		ArrayList<ArrayList<String>> myRemovedAuthorsData = myService.queryTable("Authors", myBooksData.get(0).get(1));
		if(myRemovedAuthorsData.size() != 0) 
		{
			fail("Didnt remove Author " + myBooksData.get(0).get(1));
		}
		
	}

	// UPDATE 
	// TODO test for updating book, ensure the auth and pub exists.
	@Test
	final void testUpdateEntry() 
	{
		Service myService = Service.getInstance();
		// Get all data from out Authors table
		ArrayList<ArrayList<String>> oldData = myService.queryTable("Authors", "");
		String[] myModifiedData = new String[] {oldData.get(0).get(0), "My New Name"};
		
		if(oldData.size() < 1) 
		{
			fail("Empty Authors table.");
		}
		
		if(oldData.get(0).size() != 2) 
		{
			fail("Malformed data in Authors");
		}
		
		// update the value
		myService.updateEntry("Authors", myModifiedData);
		
		// get the new version of the table and store it
		ArrayList<ArrayList<String>> newData = myService.queryTable("Authors", myModifiedData);
		
		if(newData.size() != 1) 
		{
			fail("Couldn't find modified data");
		}
		
		// restore the data to the old value
		myService.updateEntry("Authors", new String[] {oldData.get(0).get(0), oldData.get(0).get(1)});
		
		// compare
		for(int i = 0; i < newData.get(0).size(); ++i) 
		{
			if(!(myModifiedData[i].equals(newData.get(0).get(i)))) 
			{
				fail("Data mismatch, update not successful");
			}
		}
	}
};