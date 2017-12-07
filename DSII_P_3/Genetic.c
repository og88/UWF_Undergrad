/*
 * Genetic.c
 *
 *  Created on: Oct 28, 2017
 *      Author: omarg
 */
#include "BruteForce.c"

void genetic();
void generatetours();
void newMutation();
void mutationCalc();
void randomCalc(int x);
void printTours();
void brutecalc1(int x);
void printElites();


city1 Tour[20][21];         //Holds the path of the tour
int tourCount = 0; double optGen;  //The number of tours. The optimal value of the Genetic algorithm
city1 optimalGAPath[20];    //Hold the path for the optimal Genetic path

/*Generates the initial tours*/
void generatetours(int x)
{
	int i;
	for(i = 0; i < x; i++)
	{
		tourCount = i;
		genetic();
	}
}

/*Prints the Elite paths*/
void printElites()
{
	int i, j;
	for(i = 0; i < 2; i++)
	{
		for(j = 0; j < SIZE; j++)
		{
			printf("%i -> ", elites[j][i].name);
		}
		printf("0\n");
	}
}

/*initializes genetic calculation*/
void genetic()
{
	resetCities();  //resets cities
	cities[0].visit = 1; //city 0 is always visited
	optGen = INT_MAX; //optGen is set to max value
	count = 0; //count is reseted
	totalcost = 0; //cost is reseted
	randomCalc(0); //Launched random Calculation function
}

/*Initializes the new mutation calculations*/
void mutationCalc(int gen)
{
	optGen = INT_MAX; //optGen is reseted
	generatetours(tour); //Tours are genreated
	int i;
	for(i = 1; i < gen; i++)  //runs gen amount of generations
	{
		newMutation(i); //runs new mutation i times
	}
}

/*Generates a new mutation*/
void newMutation()
{
	int x = rand()%(SIZE - (tour * mut/100));  //
	tourCount = 2; //The elites are not modified
	int j;
	for(j = 0; j < SIZE+1; j++ )  //Add the elites to the first two spots
	{
		Tour[0][j] = elites[j][0];
		Tour[1][j] = elites[j][1];
	}
	int i;
	for(i = (tour * (mut/100)); i < tour; i++) //percent of mutation is used to calculate the
	{                                          //next tours
		resetCities();  //resets cities for the new mutations
		tourCount = i;  //tour count is set to i
		totalcost = 0;  //totalCost is reseted
		int j;
		for(j = 1; j <= (SIZE - x); j++)  //mut is also used to randomly keep a certain amount of cities visited
		{                                 //The rest will be replaced with brute force.
			cities[Tour[0][j].name].visit = 1; //City visited
			path[j] = Tour[0][j];              //The kept cities are added to current path
			totalcost += weights[Tour[0][j-1].name][Tour[0][j].name]; //The cost of the cities keep are added to the current total cost
		}
		count = (SIZE - x);  //Count is set to the amount kept
		brutecalc1(path[(SIZE-x)].name);  //Beutecalculator starts at a certain point
	}
}

/*modefied brute force calculator to use for new mutations*/
void brutecalc1(int x)
{
	if(count >= SIZE-1)
	{
		totalcost += weights[x][0]; //cost to travel back to 0 is added
		if(totalcost < optGen && optGen > 0) //total cost is the new optimal
		{
			optGen = totalcost;  //opt is set to total cost
			compareElites(optGen, path); //sees if the optGen is now an elite
			int y;
			for(y = 0; y < SIZE+1; y++ )
			{
				optimalGAPath[y] = path[y]; //optimal path is updated
			}
		}
		excount++;
	}
	else
	{
		int i;
		for(i = 1; i < SIZE; i++)
		{
			if(i != x && cities[i].visit != 1) //if the city to visit is not the current city and has not already been visisted
			{
				count++;  //count is updated
				cities[i].visit = 1; //city is visited
				totalcost += weights[x][i]; //cost is added to total
				path[count]= cities[i];  //city is added/ overited on the path
				brutecalc1(i);  //recursively runs algorithm
				totalcost -= weights[x][i]; //removes weight to travel
				count --; //decrements counts
				cities[i].visit = 0; //city is unvisited
			}

		}
	}
}

/*Prints out the currrent tours*/
void printTours()
{
	int i,j;
	for(i=0;i<tour;i++)
	{
		for(j = 0; j < SIZE; j++)
		{
			printf("%i -> ",Tour[i][j].name);
		}
		printf("0 \n");
	}
}

/*Calcultes a random path*/
void randomCalc(int x)
{
	int finish = 0;
	if(count >= SIZE-1)
	{
		totalcost += weights[(path[count].name)][0]; //adds weight too return to 0
		if(totalcost < optGen) //if the random path is the optimal, it replaces the optimal
		{
			compareElites(totalcost, path); //compares optimal random path with elites
			if(optGen > totalcost)
			{
				optGen = totalcost; //optimal cost is updated
			}
			int j;
			for(j = 0; j < SIZE+1; j++ )
			{
				Tour[tourCount][j] = path[j]; //new tour is added
			}
		}
		excount++;
	}
	else
	{
		while(finish !=1) //runs until full path is created
		{
			int i = rand() % SIZE ; //randomly chooses a city to visit
			if(i != x && cities[i].visit != 1) //city can  not be itself, or already visited
			{
				count++; //count is incremented
				cities[i].visit = 1; //City is visited
				totalcost += weights[x][i]; //weight is added
				path[count]= cities[i]; //city is added to path
				randomCalc(i); //recursive algorithm
				finish = 1; //program is finished
			}
		}
	}
}
