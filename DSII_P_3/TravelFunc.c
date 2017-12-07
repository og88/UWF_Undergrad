/*
 * TravelFunc.c
 *
 *  Created on: Oct 28, 2017
 *      Author: omarg
 */

/*Struct to hold city information*/
typedef struct city
{
	int name;
	int visit;
}city1;

void generateTable();
void printTable();
void generateCities();
void printCities();
void printBrutePath();
void compareElites(int optval,city1 city[]);
void resetCities();


int SIZE;  //The number of cities to Traverse
int tour;  //The number of tours in a generation
int gen;   //The number of generations
int mut;   //Percent of paths to mutate
int totalcost = 0; //The total cost of the current path
/*Count keep track of the number of cities being tested in a path
 *Count should not exceed SIZE. excount tracks how many executions brute force runs*/
int count = 0, excount = 0;
int weights [20][20]; //hold the cost of the paths weights[n][n] should always be 0 or INT_MAX
int optimalCost; //The current optimal value
int elitesVal[] = {INT_MAX,INT_MAX}; //Array hold the top two optimal values

city1 elites[21][2]; //array to hole the top two optimal paths
city1 cities[21];     //Array to hold all the cities
city1 path[21];       //Array to hold the optimal path

/*Resets every city to not visited*/
void resetCities()
{
	int i;
	for(i = 1; i < SIZE; i++) //0 is always visited so we start at 1
	{
		cities[i].visit = 0;
	}
}

/*This function compares optval with the two elites. If optval is smaller, then the optval is
 * the new elite*/
void compareElites(int optval,city1 city[])
{
	if(optval < elitesVal[0]) //Compare for the first elite
	{
		elitesVal[1] = elitesVal[0]; //The first elite becomes the second elite
		elitesVal[0] = optval; //replaces the elite values
		int i;
		for(i = 0; i <= SIZE; i++) //replaces the elite path
		{
			elites[i][0] = city[i];
		}
	}
	else if(optval < elitesVal[1]) //Compare for the second elite
	{
		elitesVal[1] = optval;  //replaces the elite values
		int i;
		for(i = 0; i <= SIZE; i++)
		{
			elites[i][1] = city[i]; //replaces the elite path
		}
	}
}

/*Create SIZE number of cities and adds them to the cities array*/
void generateCities()
{
	int i;
	city1 c;
	for(i = 0; i < SIZE; i++)
	{
		c.name = i;
		c.visit = 0;
		cities[i] = c;
	}
}

/*Prints out all cities and if they where visited*/
void printCities()
{
	int i;
	for(i = 0; i<SIZE; i++)
	{
		printf("City %i, has been visited? %i\n",cities[i].name, cities[i].visit);
	}
}

/*Prints the weight value of each path in a matrix table*/
void printTable()
{
	int i;
	for(i = 0; i < SIZE; i++)
	{
		int j;
		for(j = 0; j < SIZE; j++)
		{
			/*If statement makes sure the matrix is neat*/
			if(weights[i][j] > -10 && weights[i][j] < 10)
			{
				printf("%i  ", weights[i][j]);
			}
			else
			{
				printf("%i ", weights[i][j]);
			}
		}
		printf("\n");
	}
}

/*Reads a txt document to retrieve the travel costs*/
void generateTable()
{
	FILE *file; //Creates a file
	file = fopen("Weights.txt", "r"); //Opens "filename.txt"
	char  line[380]; //This program handles 20 cities. Line is of size (SIZE^2 - SIZE)

	int i, j;
	for(i = 0; i < SIZE; i++)
	{
		for(j = 0; j < SIZE; j++)
		{
			if(i == j)
			{
				weights[i][j] = 0; //Path from city to itself is 0
			}
			else
			{
				int weight;
				fgets(line, sizeof(line), file);  //retrieves the weight from the file
				weight = atoi(line);  //converts String to int
				weights[i][j] = weight; //Sets weight to path
			}
		}
	}
	fclose(file);  //closes file
}
