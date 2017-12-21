/*
 * MonteCarlo.c
 *
 *  Created on: Nov 14, 2017
 *      Author: omar
 *      */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

FILE *fp;
/*Write and open function are used to create a default binary file.*/
void write();
void openw();


/*Opens user binary file to retrieve information from a binary file*/
void open();

/*Closes the user binary file*/
void close();

/*Retrieves and sets user defined variable. It then passes the variables into the calculate function*/
void read();

/*Calculates The expected value aswell as the simulated value. The program also calculates the error percentage*/
void calculate(int categories, int frequences,int events, int simulation);

void launchMonteCarloAlgorithm();


struct sim
{
	int categories;
	int frequences;
	int events;
}sim;

void launchMonteCarloAlgorithm()
{
	/*openw();
	write();
	close();*/
	open();
	read();
	close();
}

void calculate(int categories, int frequences,int events, int simulation)
{
	int cat[categories], //array to hold the name of the category
	freq[categories],    //array to hold the frequencies of the categories
	i, total = 0,        //The total amount of items use to compare to
	simulatedtotal = 0;  //The simulated total of the simulation

	double analyticalTotal = 0, //The total amount of out of 100
			p[categories][2];   //array that holds the probability and the cumulitive probability

	for(i = 0; i < categories; i++)
	{
		//Names every object and gives the a set amount of times and event occurs
		cat[i] = i; //name of category example day 1
		freq[i] = rand()%frequences; //Amount a event happens. Ex: 50 tires sold on day 1
		total += freq[i];  //adds the event to total
	}
	for(i = 0; i < categories; i++)
	{
		p[i][0] = (double)freq[i] / (double)total; //Sets the probability of the even happening. Ex: day 1 sold 20 tires, week 1 sold 200 tires, P(Tire sold was on day 1) = 20/200 = 10%
		p[i][1] = 0; //Cumalitive probability set to 0 for now
		analyticalTotal += ((double)cat[i]*((p[i][0]*100))); //The expect amount out of 100 EX: If 100 sells are calculated, day 1 should appear 10 times
	}

	for(i = 0; i < categories; i++)
	{
		int x;
		for(x = 0; x <= i; x++)
		{
			p[i][1] += p[x][0]; //Calculates cumulative probability as P(X < x)
		}
	}

	for(i = 0; i <= events; i++) //Starts the simulation
	{
		double r = (double)rand()/RAND_MAX*100;  //Creates a random variable to select a category
		int x;
		for(x = 0; x < categories; x++)
		{
			if(x != 0)
			{
				if(r > (p[x-1][1] * 100) && r <= (p[x][1] * 100)) //If the random variable is in the range of the categories probability, the category is added to the total
				{
					simulatedtotal += cat[x];
				}
			}
			else
			{
				if(r <= p[0][1]*100) //If the category is the first value, it is compared to 0<r<=P(X<x)
				{
					simulatedtotal += cat[x];
				}
			}
		}
	}

	double simResults = ((double)simulatedtotal/(double)events), //Simpy take the average. Total value / the number of events
			trueResults = ((double)analyticalTotal/(double)100), //Since the analytical was taken out of 100, we divide by 100
			error = fabs(simResults-trueResults) / trueResults;  //Error percentage = abs(sim-expected)/expected
	/*Prints out information for the user*/
	printf("Simulation  %i\n\n	N: %i\n	Simulated results: %0.2f\n	Expected Value: %0.2f\n	Error: %f\n\n",simulation + 1 ,categories,
			simResults, trueResults,  error);
}

void openw()
{
	fp = fopen("SimParameters.dat", "wb");  //Opens the SimParameters.dat file a a binary file
	if (fp == NULL) {
		printf("I couldn't open SimParameters.dat for writing.\n");  //If the file does not exist, error is displayed to the user
		exit(0);
	}
}

void open()
{
	fp = fopen("SimParameters.dat", "rb"); //Opens the SimParameters.dat file a a binary file
	if (fp == NULL) {
		printf("I couldn't open results.dat for writing.\n");  //If the file does not exist, error is displayed to the user
		exit(0);
	}
}

void close()
{
	fclose(fp); //Closes the file fp;
}

void write()
{
	int num = 3; //Number of simulation to run
	fwrite(&num, sizeof(int),1,fp); //Writes the number to the binary file
	/*Three default structs to run, The user can also just add the values one at a time*/
	struct sim s1 = {.categories = 300, .frequences = 100,.events = 15};
	struct sim s2 = {.categories = 500, .frequences = 200,.events = 20};
	struct sim s3 = {.categories = 3000, .frequences = 300,.events = 25};
	/*Writes each struct onto the file*/
	fwrite(&s1, sizeof(struct sim),1,fp);
	fwrite(&s2, sizeof(struct sim),1,fp);
	fwrite(&s3, sizeof(struct sim),1,fp);
}

void read()
{
	int num; //The number of simulations to run
	fread(&num,sizeof(int),1,fp);
	int i;
	struct sim s1; //A struct that holds the simulation information
	for(i = 0; i < num; i++)
	{
		/*Takes in the aproperiate values one at a time*/
		fread(&s1.categories,sizeof(int),1,fp);
		fread(&s1.frequences,sizeof(int),1,fp);
		fread(&s1.events,sizeof(int),1,fp);
		calculate(s1.categories, s1.frequences, s1.events, i);  //runs calculation
	}
}
