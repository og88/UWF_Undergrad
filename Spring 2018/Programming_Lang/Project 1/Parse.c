#include <stdio.h>
#include "Lexan.c"

int lookahead;
void AssignStmt();
void match(int t);
void Expression();
void Term();
void Factor();

char error[CHUNK]; /*Hold the error comments*/

/*This function determines whether the next value is legal*/
void match(int t)
{
	if (lookahead == t)  //If the value is legal, the lookahead is assigned to the next element of the program
	{
		lookahead = lexan(t);
	}
	else if (t == ID)  //Custom error message for identifiers
	{
		sprintf(error, "Syntax Error Line %i\nIdentifier expected\n", lineNo);
		lookahead = ERROR;
	}
	else if (t == NUM)  //Custom error message for number
	{
		sprintf(error, "Syntax Error Line %i\nNumber expected\n", lineNo);
		lookahead = ERROR;
	}
	else if (t == ';' && lookahead == '\n')  //Custom error message for ;, compasates for \n that appears at the end of assignment
	{
		sprintf(error, "Syntax Error Line %i\nNumber expected\n", lineNo - 1);
		lookahead = ERROR;
	}
	else if (lookahead == ERROR);  //If an error already occured, will be ignored until previous errors are fixed
	else  //If the next element is not legal, the user will recieve an error
	{
		sprintf(error, "Syntax Error Line %i\n%c expected\n", lineNo, t);
		lookahead = ERROR;
	}
}

/*This function starts the assignment of an identifier*/
void AssignStmt()
{
	match(ID);  //makes sure the first element is an identifier
	if (lookahead != '=')  //Checks to see if the next element is '='
	{
		printf("Error Line %i %c istead of assignment value '=' after identifier.\n", lineNo, lookahead); //error is not found
	}
	else
	{
		match(lookahead);   //checks next variable and retreves element
		Expression();     //start expression function
		match(';');       //matches to find the end of the statement
	}
}

/*This function tests to see if any expressioins are present*/
void Expression()
{
	Term();
	while (lookahead == '+' || lookahead == '-')  //checks for addition or subtraction
	{
		match(lookahead);  //Checks if the program is legal
		Term();           //calls term
	}
}

/*This function tries to find if the assignment has any terms*/
void Term()
{
	Factor();   //calls factors
	while (lookahead == '*' || lookahead == '/')  //Checks for division and multiplication
	{
		match(lookahead);     //checks is program is legal
		Factor();     //Calls Factor
	}
}

/*Checks the factors, whether it is an id, number, or ()*/
void Factor()
{
	if (lookahead == ID)  //Checks to see that ID is legal
	{
		match(ID);
	}
	else if (lookahead == NUM)  //Checks to see if number is legal
	{
		match(NUM);
	}
	else if (lookahead == '(')   //starts an parenthesis statement
	{
		match('(');  //open parenthesis
		Expression();  //calls Expression, recursively
		match(')');  //close parenthesis
	}
	else  //error is no factors are found
	{
		sprintf(error, "Error no factors\n");
	}

}
