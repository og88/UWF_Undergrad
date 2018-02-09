#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "Parse.c"

void openF(char *str);
void Begin(char *str);


/*Initilizes all prerequisites
Sets the first two values of the hash table to begin and end
The program then tries to open all file passed onto it*/
int main(int argc, char *argv[])
{
	int i = 0;

	hashTable[1] = malloc(CHUNK);
	strcpy(hashTable[1], "begin");  //Sets the first value begin
	hashTable[2] = malloc(CHUNK);
	strcpy(hashTable[2], "end");    //Sets the second value to end

	for (i = 1; i < argc; i++)      //Loop to excecute each program
	{
		openF(argv[i]);             //Passes apropriate program to openF
	}
	return 0;

}


/*This method opens the file. If the file doeas not exist, an error is thrown
Once the file is opened, the program checks for the begin key word to start the program.
the program is then passed onto the begin function*/
void openF(char *str)
{
	file = fopen(str, "r");   //Opens file str in read mode
	if (file)
	{
		lookahead = lexan();    //sets lookahead to the first value of the parser
		if (lookahead == BEGIN)    //Program block is defined by blocks, block starts with begin
		{
			Begin(str);       //launches begin funct, passes program name to function
		}
		else if (lookahead == END)   //if the first value is end, an error is thrown
		{
			printf("Error end reached with no begin statement\n");
		}
		else  //If begin or end are not found, an error is thrown
		{
			printf("Error code block not found. Please include a begin and end statement\n");
		}
	}
	else  //If file could not be found, an error is thrown
	{
		printf("File %s does not exist!\n", str);
	}
}

/*This function sets lookahead to first identifier and launches the parser*/
void Begin(char *str)
{
	lookahead = lexan();  //retireves the next element
	while (lookahead != END && lookahead != DONE && lookahead != ERROR) //Loop allows the program to run multiply statements
	{
		if (lookahead == ID)  //Checks to see that an identifier is being set to a value
		{
			AssignStmt();  //Starts assignment with parser
		}
		else  //User tries to assign a value to non-identifier
		{
			sprintf(error, "First value of assignment must be an identifier\nLINE: %i", lineNo);
			lookahead = ERROR;
		}
	}
	if (lookahead == END) //If the program runs with no issue and has an end, the program ran successfully
	{
		int position;  //int used to keep track of position
		printf("Program %s ran Successfully!!\nIdentifiers used\n", str); //Message that tells the user their program works
		for (position = 3; position < arraySize + 1; position++)   //For loop to print out all identifiers used by the user
		{
			printf("%s\n", hashTable[position]);
		}
	}
	else if (lookahead == ERROR)  //An error was detected
	{
		printf("Warning program finished with an error\n%s\n", error);  //Tells user what erro was detected
	}
	else  //Tells user that they forget to close the code block
	{
		printf("Error program finished with no end statement\n");
	}
}