/*
 * BruteForce.c
 *
 *  Created on: Oct 28, 2017
 *      Author: omarg
 */
#include "TravelFunc.c"

void bruteforce();
void brutecalc(int x);
void printBrutePath();

city1 optimalBrutePath [21]; //optimal path for the brute for algorithm
double bruteOptimal = INT_MAX;  //optimal cost for brute force

/*Initializes the brute force operations*/
void bruteforce()
{
	resetCities();  //Resets the cities
	path[0] = cities [0]; //First destination is always 0
	path[20] = cities[0]; //last destination is also 0
	count = 1; // city 0 is alread on the path, so we start at 1
	totalcost = 0; //Cost defaults to 0
	cities[0].visit = 1; //o is already visited
	bruteOptimal = INT_MAX; //resets optimal value
	brutecalc(0); //Starts calculation at 0
}
/*Uses brute force to calculate the optimal value and path. This is acheaved by visiting all
 * possible paths*/
void brutecalc(int x)
{
	if(count >= SIZE) //Count reaches last city
	{
		totalcost = totalcost + weights[x][0]; //adds the cost from the last city back to the first
		if(totalcost < bruteOptimal) //If the current path is more optimal, it will be the new optimal
		{
			bruteOptimal = totalcost; //new brute optimal is the path cost
			compareElites(bruteOptimal, path); //compares brute force optimal with elites
			int y;
			for(y = 0; y <= SIZE; y++ )
			{
				optimalBrutePath[y] = path[y];  //copies path to optimal path
			}
		}
		totalcost = totalcost - weights[x][0]; //removes the final cost
		excount++;
	}
	else  ///recurseive algorithm to gro through all paths
	{
		int i;
		for(i = 1; i < SIZE; i++) //goes through all paths
		{
			if(i != x && cities[i].visit != 1) //city cannot visit itself, and can't visit an already visited city.
			{
				cities[i].visit = 1;  //city is now visited
				totalcost += weights[x][i]; //cost to visit city is added to total cost
				path[count] = cities[i];  //city added to/overides paths
				count++; //count incremeted (we visited a new city
				brutecalc(i); //recurse algorithm
				totalcost -= weights[x][i]; //after we return from the city, we remove its cost
				count --;  //we decrement count, since we're going backwards
				cities[i].visit = 0; //cities is now unvisited
			} //loop goes on to the next city on the list

		}
	}
}

/*Basic algorithm to print bruteforce path*/
void printBrutePath()
{
	printf("\nBrute Force optimal path %f\n", bruteOptimal); //brutForce optimal value

	int j;
	for(j = 0; j < SIZE; j++) //loop to visit the whole path
	{
		printf("%i -> ",optimalBrutePath[j].name);
	}
	printf("%i\n", optimalBrutePath[0].name);

}
