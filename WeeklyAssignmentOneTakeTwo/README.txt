Nicolae Vasile
nicolae.vasile@smoothstack.com

when launching the program, it will attempt to find/create csv files in:
WeeklyAssignments/WeeklyAssignmentOneTakeTwo/dbFiles

Once it has established the files, the program shows the fields for each table.
The primary key is the first field.

The program will ask:

	What operation would you like to do?
	(C)reate
	(R)ead
	(U)pdate
	(D)elete
	
	(Q)uit
	
and then ask:

	Which table would you like to do your <operation> on?
	(A)uthors
	(P)ublishers
	(B)ooks

lastly, it'll ask:

	Note: *ALL* commas will be interpreted as a new field, and all charachters including spaces are read.
	Enter the values (seperated by "," spaces included) for the entry you would like to <operation>


/**************************************************************************/
THE CREATE OPTION:

The program expects you to enter the value you would like the second field and beyond to be.
The empty string will generate an empty entry with its own primary key. If you enter more values
	then there are, it will ignore the extra, and add the value it can fit in the table.


/**************************************************************************/
THE READ OPTION:

The program will try to equate every value you enter to every field. This includes the primary
key. 
Sending the empty string will return the entire table .
Sending an input:<,My Value>
	will return a row with any primary key, followed by <My Value>, followed by any value in the rest of the fields


/**************************************************************************/
THE UPDATE OPTION:

The program doesnt now allow the user to change the primary key. the first value sent
is used to identify the row in the table, and the follow values will replace the corresponding fields
unless the value is the empty string, in that case- the value will remain unchanged

sending an input: <1,,123 4 Street> 
	to the Publishers table	will change the third field, the address, to 123 5th Street
	
Updating a book will try to match the first value you send to a row in the Books table.

if you didnt send an authorId or publisherId, it will update the bookeTitle with the next value (unless its the empty string, then it remans unchanged)


/**************************************************************************/
THE DELETE OPTION:

The program only looks at the first value you send, matches it to the primary key of the table youve selected

deleting an author or a publisher will delete all the books associated with their Id's





