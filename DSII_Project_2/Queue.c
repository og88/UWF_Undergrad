#include <stdio.h>
#include <math.h>
#include "Functions.c"


int main(int argc, char* argv[])
{
	int c;
	printf("How many arrivals will you like to simulate: ");
	scanf("%d", &c);
	n = (double) c;
	printf("What is the average arrivals in a time period: ");
	scanf("%d", &c);
	l = (double)c;
	printf("What is the average number served in a time period: ");
	scanf("%d", &c);
	u = (double)c;
	printf("How many services channels are there: ");
	scanf("%d", &c);
	M = (double)c;
	m = M;

	queue.isEmpty = 1;    //queue starts out empty

	while(ncom < n)
	{
		constructBatch();
		while(event < pqSize)
		{
			ProcessNextEvent();
			event++;
		}
		printResults();
		batch++;
		event = 0;
		pqSize = 0;
		timer = timer + GetNextRandomeInterval(l);
	}

	printStats();
	return 0;
}


















