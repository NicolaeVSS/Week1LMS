package com.ss.weeklyone;

enum RelationType
{
	// TODO add support for: ONETOONE and MANYTOMANY
	ONETOMANY,
	MANYTOONE
}

public class Relationship 
{
	private int parentTableIndex;
	private int targetTableIndex;
	private int parentFieldIndex;
	private int targetFieldIndex;
	private RelationType relation; 
	
	public Relationship(int parentTableIndex, int targetTableIndex, int parentFieldIndex, int targetFieldIndex, RelationType relation) 
	{
		this.parentTableIndex = parentTableIndex;
		this.targetTableIndex = targetTableIndex;
		this.parentFieldIndex = parentFieldIndex;
		this.targetFieldIndex = targetFieldIndex;
		this.relation = relation;
	}
	
	public int getParentTableIndex() 
	{
		return parentTableIndex;
	}
	
	public int getTargetTableIndex() 
	{
		return targetTableIndex;
	}

	public int getParentFieldIndex() 
	{
		return parentFieldIndex;
	}

	public int getTargetFieldIndex() 
	{
		return targetFieldIndex;
	}

	public RelationType getRelation() 
	{
		return relation;
	}
}
