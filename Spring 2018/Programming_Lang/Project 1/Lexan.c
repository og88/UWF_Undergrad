#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>

void openF(char *str);
int lexan();
int lookup(char * c);
int arraySize = 2;
int lineNo = 1;

#define CHUNK 1024 /*Limits identifiers to 1024 bits*/
#define ID 300    
#define NUM 301
#define BEGIN 400
#define END 401
#define DONE 0
#define ERROR 1

char value[CHUNK];  //holds the identifiers and numebers

FILE *file;  //file object

char* hashTable[124]; //Hashtable of strings


/*Lexan is used to determine what the next element will be*/
int lexan()
{
	bool p = true;  //boolean used to tell if the program is finished
	char ch;  //ch holds the char item retieved from the file
	while (p == true)
	{
		ch = getc(file);  //gets next char
		/*Do nothing for spaces and tabs*/
		if (ch == ' ' || ch == 32)  
		{

		}
		//increment line count with ne line character
		else if (ch == '\n')
		{
			lineNo++;
		}
		//ignore comments
		else if (ch == '`')
		{
			while (ch != '\n')  //Program goes through the comment line ignoring the line.
			{
				ch = getc(file);
			}
		}
		/*This is the ascii values for numbers*/
		else if (ch >= 48 && ch <= 57)
		{
			value[0] = ch; //apends chars to value string
			int i = 1;
			//retrieve full number
			while (ch >= 48 && ch <= 57)  //loop that appends numbers
			{
				value[i] = ch; 
				i++;
				ch = getc(file); //retrieves next values
			}
			ungetc(ch, file);  //If next value is not a number, go back to avoid problems with getc
			memset(value, 0, sizeof(value)); //clears value for next use
			return NUM; 
		}
		/*ascii values for letter. Each identifier must start with a
		letter.*/
		else if (ch >= 65 && ch <= 122)  //identifiers must start with letter
		{
			int i = 1;
			value[0] = ch;   //Appends first letter to string
			ch = getc(file);   //gets next char from file
			while (ch != ' ' && ch != '\n' && ch != ';' && ch != '(' && ch != ')')   //appends values to value string, Special symbols
			{
				value[i] = ch;
				i++;
				ch = getc(file);  //retrieve next char
			}
			ungetc(ch, file);   //If next value is not a number, go back to avoid problems with getc
			int pos = lookup(value);   //Find the position of identifier

			memset(value, 0, sizeof(value));  //Reset value for next use

			if (pos == 1)    //if identifier is begin, return begin
			{
				return BEGIN;
			}
			else if (pos == 2)   //if identifier is end, return end
			{
				return END;
			}
			else  //Return ID for any other identifier
			{
				return ID;
			}
		}

		else if (ch == EOF)  //If the end of 
		{
			p = false;   //ends loop when end of file is reached
			return DONE;   //return Done when finished
		}
		else
		{
			return ch;  //returns char if no match found
		}
	}
	return 0;
}

/*Function to lookup the position of an identifier*/
int lookup(char * c)
{
	char* temp = malloc(CHUNK); //creates a temporary temp object
	int position = 1; //Tracks position of identifiers

	for (position = 1; position < arraySize + 1; position++)  //Loops through hash table until finished or identifier is found
	{
		strcpy(temp, hashTable[position]); //temporarly copies identifiers to temp
		if (strcmp(c, temp) == 0)  //Position is found
		{
			free(temp); //frees up space used by temp
			return position; 
		}
	}
	arraySize++;  //array size is increased to add new value
	hashTable[arraySize] = malloc(CHUNK);  //allocates memory use for array
	strcpy(hashTable[arraySize], c);  //adds new identifier to table
	free(temp);  //frees memory used for temp
	return 0;
}