/*
 * TravelingSalesMan.c
 *
 *  Created on: Oct 28, 2017
 *      Author: omarg
 */
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <time.h>
#include "Genetic.c"

void brut();
void GA();
void toString();

clock_t start; //holds the time at the start of a process
clock_t end;   //holds the end value of the process
clock_t brutTime; //The time brute force took to complete
clock_t GATime;  //The time the Genetic Algorithm takes


int main(int argc, char *argv[])
{
	printf("How many Cities? (1-20)\n");
	scanf("%i",&SIZE);
	printf("How many tours? (1-20)\n");
	scanf("%i", &tour);
	printf("How many Generations?\n");
	scanf("%i", &gen);
	printf("What percentage of Generations should be mutated?\n");
	scanf("%i", &mut);

	generateTable();  //Generates the weights
	generateCities(); //Creates SIZE number of cities
	brut();           //Brute force calculation
	GA();             //Genetic Algorithm calculation
	toString();       //Prints information
	return 0;
}

/*Prints out information in a nice word format*/
void toString()
{
	double opt = 0;
	if((double)GATime != 0)
	{
		opt = ((bruteOptimal/optGen) + (double)(brutTime/GATime)/10); //calculate optimal
	}
	else
	{
		opt = (bruteOptimal/optGen) + ((double)(brutTime - GATime)/50); //calculates optimal
	}
	printf("\nCities simulated : %i\n"
			"Optimal for Brute Force is : %.0f "
			"Time for Brute Force : %ld\n"
			"Cost For Genetic Algorithm is: %.0f"
			" Genetic Algorithm Time is : %ld\n"
			"Percent optimal : %.2f%%\n",SIZE, bruteOptimal, brutTime, optGen, GATime, (opt*100));

}

/*Start bruteforce and calculates the time it takes*/
void brut()
{
	start = clock();
	bruteforce();
	end = clock();
	brutTime = (double)(end -start)/CLOCKS_PER_SEC;
	//printBrutePath();
}

/*Start the genetic algorithm and calculates the time*/
void GA()
{
	start = clock();
	mutationCalc(gen);
	end = clock();
	GATime = (double)(end -start)/CLOCKS_PER_SEC;
	//printElites();
}
