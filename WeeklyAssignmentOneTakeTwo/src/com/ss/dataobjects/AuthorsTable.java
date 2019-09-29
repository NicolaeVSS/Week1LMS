package com.ss.dataobjects;

import java.io.*;

public class AuthorsTable extends Table
{	
	public AuthorsTable() 
	{
		super(new String[] {"authorId", "authorName"},"Authors","./dbFiles/Authors.csv");
	}
}
