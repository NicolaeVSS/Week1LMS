import java.util.*;

import com.ss.service.Service;

public class Main {

	public static void main(String[] args) 
	{
		Service library = Service.getInstance();
		Scanner input = new Scanner(System.in);
		
		String op; // stores the user's desired operation
		String tableName; // stores the user's target for the desired operation
		String[] enteredFields; // stores the user's field values to check against in the target table
		ArrayList<ArrayList<String>> returnedRows; // store the result of a read operation
		
		/*
		// FOR DEBUGGING
		ArrayList<ArrayList<String>> myResult = library.queryTableComplement(false ,"Books", new String[] {"1"});
		
		System.out.println("My Query returned " + myResult.size() + " rows");
		
		System.out.println(make2DArrayListLegible(myResult));
		 */
		
		do 
		{
			System.out.println("/*MAIN MENUE*************************************************************************/");
			System.out.println(library.getTableSchemas());
			System.out.println("What operation would you like to do?\n(C)reate\n(R)ead\n(U)pdate\n(D)elete\n\n(Q)uit");
			op = String.valueOf(Character.toUpperCase(input.next().charAt(0)));
			System.out.println();
			
			switch(op) 
			{
			case "C":
				op = "Create";
				break;
			case "R":
				op = "Read";
				break;
			case "U":
				op = "Update";
				break;
			case "D":
				op = "Delete";
				break;
			case "Q":
				op = "Quit";
				break;
			}
			
			switch(op) 
			{
			case "Create":
				tableName = getTableSelection(op);
				System.out.println(library.getTableSchemas());
				enteredFields = getFieldSelection(op);
				
				library.addEntry(tableName, enteredFields);
				break;
			case "Read":
				tableName = getTableSelection(op);
				System.out.println(library.getTableSchemas());
				enteredFields = getFieldSelection(op);
				
				System.out.println(Service.make2DArrayListLegible(library.queryTable(tableName, enteredFields)));
				break;
			case "Update":
				tableName = getTableSelection(op);
				System.out.println(library.getTableSchemas());
				System.out.println("Note: Your first value will match to the primary key of " + tableName + " and the values overwrite the corresponding fields");
				enteredFields = getFieldSelection(op);
				
				library.updateEntry(tableName, enteredFields);
				break;
			case "Delete":
				tableName = getTableSelection(op);
				System.out.println(library.getTableSchemas());
				System.out.println("Note: Only the first value sent will be considered. It must be the primary key of row you'd like to remove.");
				enteredFields = getFieldSelection(op);
				
				library.removeEntry(tableName, enteredFields);
				break;
			case "Quit":
				return;
			default:
				System.out.println("Menue option not recognized, try again.");
				continue;
			}
		}
		while(true);
	}
	
	public static String getTableSelection(String op) 
	{
		Scanner input = new Scanner(System.in);
		
		System.out.println("Which table would you like to do your " + op + " on?");
		System.out.println("(A)uthors\n(P)ublishers\n(B)ooks\n");
		
		String table = String.valueOf(Character.toUpperCase(input.next().charAt(0)));
		
		switch(table) 
		{
		case "A":
			table = "Authors";
			break;
		case "P":
			table = "Publishers";
			break;
		case "B":
			table = "Books";
			break;
		default:
			table = "unknown";
		}
		
		return table;
	}
	
	
	public static String[] getFieldSelection(String op) 
	{
		Scanner input = new Scanner(System.in);

		System.out.println("Note: *ALL* commas will be interpreted as a new field, and all charachters including spaces are read.");
		System.out.println("Enter the values (seperated by \",\" spaces included) for the entry you would like to " + op);
		
		while(input.hasNextLine()) 
		{
			return input.nextLine().split(",");
		}
		
		return new String[0];
	}
}
