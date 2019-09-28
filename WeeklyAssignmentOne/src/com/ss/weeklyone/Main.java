package com.ss.weeklyone;

import java.util.*;

public class Main 
{

	public static void main(String[] args) 
	{	
		DatabaseConnection db = DatabaseConnection.getInstance();

		System.err.println("Hello World!");
		
		// Setup our tables
		db.createTable("Authors",	"authorId", 	"authorName");
		db.createTable("Publishers","publisherId", 	"publisherName","publisherAddress");
		db.createTable("Books",		"bookID", 		"authorId", 	"publisherId", 		"bookName");

		// TODO add relationships to header and check if they already exist
		// Establish relationship between Authors.authorId and Books.authorId
		int parentTableIndex = db.getTableIndex("Authors");
		int targetTableIndex = db.getTableIndex("Books");
		int parentFieldIndex = db.getTablesRef().get(parentTableIndex).getFieldIndex("authorId");
		int targetFieldIndex = db.getTablesRef().get(targetTableIndex).getFieldIndex("authorId");
		db.addRelationship(parentTableIndex, targetTableIndex, parentFieldIndex, targetFieldIndex, RelationType.ONETOMANY);

		// Establish relationship between Books.authorId and Authors.authorID (the reverse of the previous relation)
		db.addRelationship(targetTableIndex, parentTableIndex, targetFieldIndex, parentFieldIndex, RelationType.MANYTOONE);
		
		// Establish relationship between Publishers.publisherId and Books.publisherId
		parentTableIndex = db.getTableIndex("Publishers");
		targetTableIndex = db.getTableIndex("Books");
		parentFieldIndex = db.getTablesRef().get(parentTableIndex).getFieldIndex("publisherId");
		targetFieldIndex = db.getTablesRef().get(targetTableIndex).getFieldIndex("publisherId");
		db.addRelationship(parentTableIndex, targetTableIndex, parentFieldIndex, targetFieldIndex, RelationType.ONETOMANY);

		// Establish relationship between Books.pubisherId and Publisher.publisherId (the reverse of the previous relation)
		db.addRelationship(targetTableIndex, parentTableIndex, targetFieldIndex, parentFieldIndex, RelationType.MANYTOONE);
		
		// TODO verify relationships
		String arr = Arrays.toString(db.getTablesRef().get(db.getTableIndex("Authors")).getRelationships().toArray());
		System.out.println("Authors is related to: " + arr);
		
		// TODO Insert some testing data

		// TODO Query the test data
		
		// TODO update some testing data
		
		// TODO Remove some testing data
		
		//System.out.println(db.getTablesRef().get(2).getTableName());
		//System.out.println(db.getTablesRef().get(2).getPrimaryKey());
		//System.out.println(Arrays.toString(db.getTablesRef().get(2).getFields()));
	}

}
