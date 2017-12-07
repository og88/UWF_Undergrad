/*
 * Similarities.c
 *
 *  Created on: Dec 7, 2017
 *      Author: omarg
 */
#include "twoSequences.c"
#include <string.h>

void part2();
void sim(char* c[], int d[], int i);
char comp2(int sHort,int lOng, int compare);


/*This method reads an int and multiple strings from a file
 * The int is used to determine how many sentences there are.
 * The strings are compare and a table is filled up to show whether there are similar*/
void part2()
{
	FILE * fp;
	char * line = NULL, * line2 = NULL;
	size_t len = 0;
	ssize_t read,read1;

	fp = fopen("multipleSequences.txt", "r");
	if (fp == NULL)
	{
		exit(EXIT_FAILURE);
	}



	int i;
	if(fscanf(fp, "%d", &i))
	{
	char * c[i];
	int d[i];
	int x = 0;
	while (((read = getline(&line, &len, fp)) != -1) && x <= i)
	{
		read -= 1;
		c[x] = malloc(strlen(line) + 1);
		strcpy(c[x], line);
		d[x] = read;
		//printf("%s\n%s\n",c[0], line);
		x++;
	}
	sim(c ,d, i);
	printf("\n");
	}
	else
	{
		printf("Error: Number of sentences not found");
	}




	fclose(fp);
	if (line)
	{
		free(line);
	}
	exit(EXIT_SUCCESS);
}


/*This method determines whether the the two compare sequences are High similarity,
 * Medium similarity,  Low similarity Depending on the length of the short, long, and LCS*/
char comp2(int sHort,int lOng, int compare)
{
	char c;
	if((((double)sHort/(double) lOng) >= .9) && (((double) compare/(double)sHort) > .8))
	{
		printf("High similar\n\n");
		c = 'H';
	}

	else if((((double)sHort/(double) lOng) >= .8) && (((double) compare/(double)sHort) > .6))
	{
		printf("Medium similar\n\n");
		c = 'M';
	}
	else if((((double)sHort/(double) lOng) >= .6) && (((double) compare/(double)sHort) > .5))
	{
		printf("Low similar\n\n");
		c = 'L';
	}
	else
	{
		printf("Dissimilar\n\n");
		c = 'D';
	}
	return c;
}

/*This method computes assigns two methods with each other to be compared.
 * The method then records the length of the LCS and passes it to comp2*/
void sim(char * c[], int d[], int i)
{
	int x, y;
	char t[i][i];
	for(x = 0; x < i; x++)
	{
		for(y = 0; y < i; y++)
		{
			t[x][y] = '-';
		}
	}

	for(y = 0; y < i; y++)
	{
		for(x = 0; x < y; x++)
		{
			//printf("%i %i", x, y);
			if(x != y)
			{
				printf("Line 1 : %s\nLine 2 : %s\n",c[x], c[y]);
				if(d[x] <= d[y])
				{
					int len = compare(c[x], d[x], c[y], d[y], 0);
					printf("%i\n", len);
					char s = comp2(d[x], d[y], len);
					t[x][y] = s;
				}
				else
				{
					int len = compare(c[y], d[y], c[x], d[x], 0);
					printf("%i\n", len);
					char s = comp2(d[x], d[y], len);
					t[x][y] = s;
				}
			}
		}
	}

	for(x = 0; x < i; x++)
	{
		printf("\n");
		for(y = 0; y < i; y++)
		{
			printf("%c",t[x][y]);;
		}
	}
}
