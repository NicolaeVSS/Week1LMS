package com.ss.exception;

public class ImproperDaoNameException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7216466705991000160L;

	public ImproperDaoNameException() 
	{
		super("Table not found.");
	}
}