/*
 * Omar Garcia
 * cmdLine.c
 * Programming project 1
 *
 * This program will simulate the UNIX command line. To accomplish this, it must first create a database of directories and files.
 * This is done by creating a tree where each node has a child and a sibling. Only directories can has children, files can not.
 * The children are the directories and files inside a directory. The siblings are the other files inside the directory the node is in.
 * Like in the literal sense, the child's sibling are children of the parent also. To keep it simple, we will limit it to one child
 * with one sibling and that sibling can have more and so on.
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cmdLineFunctions.h"
#define LINE_LENGTH 100  //line length determines how long of a line the variable line can hold.
#define MAX_PARAMS 20    //token can hold as many parameters as possible. For this project params will need to hold a max of 3.
#define PARAM_LENGTH 50  //For this project, I don't expect commands or names to be over 50 characters long.


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*
 * These are the comparison strings. There is a char array for every command the commandline utilizes.
 * These arrays are used in the if else statements below.
 */
char userName[]  = {'c','o','f','f','e','y','\0'};
char LS[]    = "ls";               // lists all files and directories in the current directory, indicating which (file or directory) it is
char MKDIR[] = "mkdir";            // creates a new directory if it does not already exist
char CD[]    = "cd";               // changes into specified directory if it exists
char CDB[]   = "cd..";             // changes to the parent directory
char PWD[]   = "pwd";              // specifies the current directory as: root/<yourname>/nextdir/etc/
char ADDF[]  = "addf";             // adds a file to the current directory
char MV[]    = "mv";               // change the name of the file or directory to the new name
char CP[]    = "cp";               // copy file or folder to the new name
char RM[]    = "rm";               // locate and remove the file or directory
char BYE[]   = "bye";              // ends the session
char WHERE[] = "whereis";          // show path to first occurrence of file or directory if it exists

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*
 *These are the variable initializers. These variables are specific to the cmdLine source files and they are only used there.
 */
char* s = " \n"; //@s is used by strtok to split the string up. strtok looks for s and splits the char before
//and after s into different array locations in token.
char* token;     //@token holds the parameters that are used by the command line. The first parameter is the command itself.
//if the command requires more than one parameter it will store the needed parameters.
char params[MAX_PARAMS][PARAM_LENGTH]; //@params is an array of arrays. It stores the command any other parameter it utilizes.
char line[LINE_LENGTH]; //Line is used to hold a whole line from a file.

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*Used to specify the current directory the program should work on. Set to 0 at start since  no directory exists.*/
int counter = 0;

/*Used to determine if the node to be deleted is the child of the directory or a sibling of one of the other nodes. Set to one
 * at start since no chidren exist yet.*/
int isFirst = 1;

/*
 * layer will hold the parent directories. This pointer array acts as the activity lof for this command line.
 */
node* layer[15];
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

int main(int argc, char* argv[])
{
	node* user = NULL;
	user = malloc(sizeof(node));
	user -> child = NULL;
	user -> sib = NULL;
	counter=0;
	layer[counter] = user;
	strcpy(layer[counter]->name, userName);

	FILE* file = fopen("commands.txt", "r");

	while(fgets(line, 100, file))
	{
		printf("$ ");
		token = strtok(line, s);
		int i = 0;
		int count = 0;
		while(token != NULL)
		{
			strcpy(params[i],token);
			token = strtok(NULL, s);
			//printf("\n %s\n", params[i]);
			i++;
		}
		if(strcmp(params[count], LS) == 0)
		{
			printf("%s\n", params[count]);
			ls(layer[counter]->child);
		}
		else if(strcmp(params[count], MKDIR) == 0)
		{
			printf("%s %s\n", params[count], params[count+1]);
			mkdir(params[count + 1]);
		}

		else if(strcmp(params[count], CD) == 0)
		{
			printf("%s %s\n", params[count], params[count+1]);
			cd(params[count + 1]);
		}

		else if(strcmp(params[count], CDB) == 0)
		{
			printf("%s\n", params[count]);
			cdB();
		}

		else if(strcmp(params[count], PWD) == 0)
		{
			printf("%s\n", params[count]);
			pwd();
		}
		else if(strcmp(params[count], ADDF) == 0)
		{
			printf("%s %s\n", params[count], params[count + 1]);
			addF(params[count + 1]);
		}
		else if(strcmp(params[count], MV) == 0)
		{
			printf("%s %s %s\n", params[count], params[count + 1], params[count + 2]);
			mv(params[count + 1], params[count + 2]);
		}
		else if(strcmp(params[count], CP) == 0)
		{
			printf("%s %s %s\n", params[count], params[count + 1],params[count + 2]);
			cp(params[count + 1], params[count + 2]);
		}
		else if(strcmp(params[count], RM) == 0)
		{
			printf("%s %s\n", params[count], params[count + 1]);
			rm(params[count + 1]);
		}
		else if(strcmp(params[count], WHERE) == 0)
		{
			printf("%s %s\n", params[count], params[count + 1]);
			whereIs(params[count + 1]);
		}
		else if(strcmp(params[count], BYE) == 0)
		{
			printf("%s\n", params[count]);
			fclose(file);
			exit(1);
		}
		else
		{
			printf("%s command not found\n", params[count]);
		}
	}
	fclose(file);
	return 0;
}
