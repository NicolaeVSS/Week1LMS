package com.ss.dataobjects;

import java.io.*;
import java.util.Arrays;

public class BooksTable extends Table
{
	public BooksTable() 
	{
		super(new String[] {"bookId","authorId","publisherId","bookTitle"},"Books","./dbFiles/Books.csv");
	}
}
