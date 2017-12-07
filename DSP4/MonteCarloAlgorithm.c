/*
 * MonteCarloAldgorithm.c
 *
 *  Created on: Nov 14, 2017
 *      Author: omarg
 */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <math.h>

FILE *fp; //File pointer used to open the required files
int run=0; //The number of runs performed

/*Calculates the Monte Carlo Calculation
 * It first creates the actual database, recording information as needed.
 * It then runs a simulation by randomly selecting chips to test
 * It then passes the information through the appropriate method to print the information for the user*/
void Calculate(int c[]);

/*Takes in an int of the information provided by the user
 * Prints out the information in a nicely formated paragraph*/
void runToString(int c[]);

/*Takes in an array of double containing the information
 * Base, Exponent, Probability of failure to detect bad chips, Probability of good chips, and Percent of bad chips found
 * Prints a nicely formated summary of the analyzed run*/
void analyzeToString(double c[]);

/*Begins the program.
 * It reads the configuration from the required user document
 * If the files are not found, the program will warn the user than quite.
 * If the files are found, the program executes.*/
void launchMonteCarlo();

/*Write four default configuration files*/
void write1();


void launchMonteCarlo()
{
	int c[4]; //Array that holds the information provided by the c"n".txt file the user provides
	int i,f;  //Variable used for loops, f loops 4 times for the four user files and i four times for the four ints provided by the user
	for(f = 1;f<=4;f++)
	{
		char name[128]; //Creates a String
		snprintf(name, sizeof(name), "c%d.txt",f); //The files should be named c1.txt ... c4.txt. Using a loop to open the file saves us from repetitive coding.
		fp = fopen(name,"r"); //opens file with "name"
		if (fp == NULL) { //If the file is not found, the program warns the user and exits
			printf("I couldn't open %s for writing.\nPlease Ensure the proper files are in the proper directory", name);
			exit(0);
		}
		else
		{
			for(i = 0; i < 5; i++) //Loop to pull the information out of the file
			{
				fscanf(fp, "%i", &c[i]);
			}
			fclose(fp); //Closes file when finished with it
			Calculate(c); //Runs calculate function
		}
	}
}

void write1()
{
	//Writes default values to each file
	fp = fopen("c1.txt", "w");
	fprintf(fp,"%d\n%d\n%d\n%d\n%d",100,2000,24,7,30);
	fclose(fp);
	fp = fopen("c2.txt", "w");
	fprintf(fp,"%d\n%d\n%d\n%d\n%d",100,2000,10,10,50);
	fclose(fp);
	fp = fopen("c3.txt", "w");
	fprintf(fp,"%d\n%d\n%d\n%d\n%d",500,1000,10,10,50);
	fclose(fp);
	fp = fopen("c4.txt", "w");
	fprintf(fp,"%d\n%d\n%d\n%d\n%d",500,1000,1,1,50);
	fclose(fp);
}

void Calculate(int c[])
{
	run++;
	printf("\nRunning:\n"); //Provides the user with feedback on what is running
	runToString(c);
	srand(time(NULL));

	printf("Genrating data set:\n"); //Starts to generate data set, This is creates all the txt files and chips good/bad. This can take a while if enough objects a created
	int i,f,					 //i and f are used for looping
	r,   					     //r is used for the random variables
	totalBad = 0,			     //The actual amount of bad chips
	totalCreated = 0,            //The total amount of bad batches
	totalAnalyzed = 0;           //The amount of bad batches caught by the simulation, not bad chips


	for(i = 0; i <c[0]; i++)
	{
		char name[128];
		snprintf(name, sizeof(name), "ds%d.txt",i); //Creates the a name string with the appropriate file name
		fp = fopen(name, "w");                      //opens/ creates a new file. This will overwrite previous files with the same name
		r = rand() % 100;                           //Random number to determine if batch is bad or good
		if(r>=0 && r<c[2])                          //Compare the random number with the percentage of bad chips
		{
			totalCreated++;                         //Number of bad batches is incremented
			for(f = 0; f <c[1];f++)
			{
				r = rand() % 100;                   //Random number is use again to determine if a chip is bad
				if(r>=0 && r<c[3])
				{
					totalBad++;                     //Increments amount of bad chips
					fprintf(fp,"b\n");              //Writes b for bad in a file
				}
				else
				{
					fprintf(fp,"g\n");              //Writes g for good if everthing is fine
				}
			}
			printf("Create bad set batch # %4.1d, totBad = %4.d, total = %d badpct = %d\n",i,totalBad, c[1],c[3] );  //Displays information to the user
		}
		else
		{
			for(f = 0; f <c[1];f++)
			{

				fprintf(fp,"g\n");  //if the batch is good, all the chips are labeled as good

			}
		}
		fclose(fp); //closes file
	}
	printf("Total bad sets = %d\n\n", totalCreated);  //Displays the amount of bad batches created

	printf("Analyzing Data Sets\n");  //Starts the simulation
	for(i = 0; i <c[0]; i++)  //Checks every batch
	{
		char name[128];
		snprintf(name, sizeof(name), "ds%d.txt",i);
		fp = fopen(name, "r"); //Same as above, but we are reading data
		char y;
		int count = 0, //Keeps track of the events
				r;     //Used to determine whether to check a chip


		while(((y = fgetc(fp)) != EOF) && count < c[4]) //Runns until the end of the file is reached or the number of events is fullfilled
		{
			if((r = rand()%100) < c[4]) //Uses The number of events to determine whether or not to check a chip
			{
				count++;                //Increases the counter for events
				if(y != '\n' && y != '\r')  //Ignores newlines and spaces
				{
					if(y == 'b')          //If the chip is bad, we are done. The batch is bad
					{
						printf("batch %3.i is a bad batch\n",i);  //Tells user that bad batch was found
						totalAnalyzed ++;                         //increments bad batch count
						break;
					}
				}
			}
		}
		fclose(fp) ;
	}
	printf("Total bad sets = %d\n\n", totalAnalyzed);  //Displays the amount of bad batches

	double b[5]; //holds information needed for the summary
	b[0] = 1-((double)c[3]/100);
	b[1] = c[4];
	b[2] = pow(b[0],c[4]);
	b[3] = 1 - b[2];
	b[4] = (double)totalAnalyzed/(double)totalCreated;



	printf("Summary\n\nRun %i\n",run); //Prints out the summary
	runToString(c);
	analyzeToString(b);
}

void analyzeToString(double b[])
{
	printf("Base %f exponent %i\n"
			"P(failure to detect bad items) = %f\n"
			"P(batch is good) = %f\n"
			"Percentage of bad batches detected = %.f%%\n\n", b[0], (int)b[1], b[2],b[3], b[4]*100);

}

void runToString(int c[])
{
	printf("Number of batches of items:                   %d\n"
			"Number of items in each batch                 %d\n"
			"Percentage of batches containing bad items    %d%%\n"
			"Percentage of items that are bad in a bad set %d%%\n"
			"Items sampled from each set                   %d\n",c[0],c[1],c[2],c[3],c[4]);


}
