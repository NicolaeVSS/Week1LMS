package com.ss.dataobjects;

import java.io.*;

public class PublishersTable extends Table
{
	public PublishersTable() 
	{
		super(new String[] {"publisherId", "publisherName","publisherAddr"},"Publishers","./dbFiles/Publishers.csv");
	}
}
