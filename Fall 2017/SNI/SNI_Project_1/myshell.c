#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "parse.c"

int main(int argc, char* argv[])
{
	pid_t pid;                    //PID number
	int status = 0;               //Status returned by child. used in wait
	int debug = 0, i;             //debug indicate whether we need to run printParam. i is used as a counter for loops.



	while (status != 1)          //Status will return 0 if exec runs correctly. If user inputs "exit" int to command line,
	{                            //The program will end.


		Param_t* p1 = malloc(sizeof(Param_t));   //creates a Param_t object named p1
		p1 = parseTokens();                            //set p1 to the return value of parseTokens()

		if((pid = fork()) < 0) 					 //Checks to make sure our fork() runs
		{
			perror("fork failure"); 			 //If fork fails, it will tell the user.
			exit(1);
		}

		if(pid == 0)            					 //If statement checks to see if the current process is a child process
		{

			if(p1->inputRedirect)                    //if statement to see if an inputRedorect is needed
			{
				freopen(p1->inputRedirect, "r", stdin);
			}
			if(p1->outputRedirect)                    //if statement to see if an outputRedorect is needed
			{
				freopen(p1->outputRedirect, "w", stdout);
			}


			i = 0;                                     //i is used as acounter to find debug
			while((debug == 0) && (i < argc))          //loops until debug is found, or we run out of arguments
			{
				if(strcmp(argv[i], "-Debug") == 0)
				{
					printParams(p1);                    //prints debug information if debug is found
				}

				i++;
			}

			//These are the if else statements that run the appropriate actions the user asks for.
			//files run with execvp. exit is the terminating command, so it exits with value 1
			//if the process fails because of an invalid command, the process exits with 2, to indicate invalid command
			if(strcmp(p1->argumentVector[0], "exit") == 0)
			{
				_exit(2);
			}

			else
			{
				execvp(p1->argumentVector[0],p1->argumentVector);     //executes command line code using execvp. execvp takes in the file name and its arguments. This is more efficient than using execl, and execlp
				exit(1);                                              //if exec fails, it exits with the value 1, to tell the shell an invalid command was inputed.
			}
		}
		else
		{
			if(!p1->background)                        //Checks to see if process needs to run in the background
			{

				wait(&status);                                //waits for children to die. Inputs exit values into status
				if(WEXITSTATUS(status) == 1)                  //if exit is 1, the program warns the user of an invalid command if its 2, than it is an exit command.
				{
					printf("Error: invalid command\n");
					status = 0;                               //status is set to 0 to continue the loop
				}
				else if(WEXITSTATUS(status) == 0)
				{
					status = 0;                               //If exit status is 0, then status is set to 0 to continue receiving commands
				}
				else if(WEXITSTATUS(status) == 2)
				{
					status = 1;
				}
			}
		}                                                 //entering command exit will return a non 0 value, which will end the loop.
	}
	return 0;
}


