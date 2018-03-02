void errorHandler(int t, int lookahead);
void programErrors(int lookahead, char* str);
void readFile(char *str);


char error[CHUNK]; /*Hold the error comments*/
FILE *file;  //file object

/*Simple method that handles error. Assigns a message that is appropriate for the error*/
void errorHandler(int t, int lookahead)
{
	if (lookahead == ERROR);  //If an error already occured, will be ignored until previous errors are fixed
	else if (t == ID)  //Custom error message for identifiers
	{
		sprintf(error, "Syntax Error Line %i, Identifier expected\n", lineNo);
	}
	else if (t == NUM)  //Custom error message for number
	{
		sprintf(error, "Syntax Error Line %i, Number expected\n", lineNo);
	}
	else if (t == ';')  //Custom error message for ;, compasates for \n that appears at the end of assignment
	{
		sprintf(error, "Syntax Error Line %i, semicolon expected\n", lineNo - 1);
	}
	else if (lookahead == 0)
	{
		printf("Undeclaresd identifier");
	}
	else  //If the next element is not legal, the user will recieve an error
	{
		sprintf(error, "Syntax Error Line %i, %c expected\n", lineNo, t);
	}
}


/*Handles the overall errors of the program. Prints out the error for the user*/
void programErrors(int lookahead, char* str)
{
	if (lookahead == ERROR)  //An error was detected
	{
		printf("Warning program finished with an error\n%s\n\n", error);  //Tells user what error was detected
		readFile(str);
	}
	else  //Tells user that they forget to close the code block
	{

		printf("Error program finished with no end statement\n\n");
		readFile(str);
	}
}

/*Reads the entire file and prints it for the user*/
void readFile(char* str)
{
	char ch;
	file = fopen(str, "r");   //Opens file str in read mode 
	ch = getc(file);          //Read next input
	while (ch != EOF)              //Reads entire file until the end
	{
		printf("%c", ch);
		ch = getc(file);
	}
	printf("\n");
}