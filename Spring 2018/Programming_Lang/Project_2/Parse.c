#include <stdio.h>
#include "Lexan.c"
#include "ErrorHandler.c"
#include "Stringer.c"

int lookahead;
void AssignStmt();
void match(int t);
void Expression();
void Term();
void Factor();

/*This function determines whether the next value is legal*/
void match(int t)
{
	if (lookahead != ERROR && lookahead == t)  //If the value is legal, the lookahead is assigned to the next element of the program
	{
		lookahead = lexan(t);
	}
	else  //If the next element is not legal, the user will recieve an error
	{
		errorHandler(t, lookahead);
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
		strcat(program, "= ");
		match(lookahead);   //checks next variable and retreves element
		Expression();     //start expression function
		strcat(program, "\n");
		match(';');       //matches to find the end of the statement
	}
}

/*This function tests to see if any expressioins are present*/
void Expression()
{
	Term();
	while (lookahead == '+' || lookahead == '-')  //checks for addition or subtraction
	{
		add(lookahead, program);
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
		add(lookahead, program);
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
		if (lookahead == 0)
		{
			printf("Error \n");
		}
	}
	else if (lookahead == NUM)  //Checks to see if number is legal
	{
		match(NUM);   
	}
	else if (lookahead == '(')   //starts an parenthesis statement
	{
		add(lookahead, program);
		match('(');  //open parenthesis
		Expression();  //calls Expression, recursively
		add(lookahead, program);
		match(')');  //close parenthesis
	}
	else  //error is no factors are found
	{
		sprintf(error, "Error no factors\n");
	}

}




