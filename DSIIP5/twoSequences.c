/*
 * twoSequences.c
 *
 *  Created on: Dec 7, 2017
 *      Author: omarg
 */
int compare(char * line, int len, char * line2, int read1, int true);
void find(int** c, int x, int y, char * line1, char * line2);
void part1();

void part1()
{
	FILE * fp;
	char * line = NULL, * line2 = NULL;
	size_t len = 0, len2 = 0;
	ssize_t read,read1;

	fp = fopen("twoSequences.txt", "r");
	if (fp == NULL)
	{
		exit(EXIT_FAILURE);
	}

	while ((read = getline(&line, &len, fp)) != -1)
	{
		if((read1 = getline(&line2, &len2, fp)) != -1)
		{
			read -= 1;
			//printf("Line 1 : %sLine 2 : %s\n", line, line2);
			if(read < read1)
			{
				compare(line , read, line2, read1, 1);
			}
			else
			{
				compare(line2 , read1, line, read, 1);
			}
		}
	}

	fclose(fp);
	if (line)
	{
		free(line);
	}
}


/*This method uses the c matrix to retrieve the LCS*/
void find(int** c, int x, int y, char * line1, char * line2)
{
	int i, f;
	char c1;

	if((x >= 0) && (y>=0))
	{

		if(c[x][y-1] >= c[x][y])
		{
			find(c, x, y-1, line1, line2);
		}
		else if(c[x-1][y] >= c[x][y])
		{
			find(c, x-1, y, line1, line2);
		}
		else if(c[x-1][y-1] == (c[x][y] - 1))
		{
			c1 = line1[x-1];
			find(c, x-1, y-1, line1, line2);
			printf("%c", c1);
		}

	}
}

/*This method calculates the c matrix for LCS. It does this by going through each character from the
 * second sequence and compares them to the characters of the first sequence*/
int compare(char * line, int read, char * line2, int read1, int true)
{

	int i, f;

	int** c;

	c = malloc((read + 1) * sizeof(*c));
	for(i = 0; i < (read1 + 1); i++)
	{
		c[i] = malloc((read1 + 1) * sizeof(*c[i]));
	}

	for(i = 0; i < read + 1; i++)
	{
		c[i][0] = 0;
	}
	for(i = 0; i < read1 + 1; i++)
	{
		c[0][i] = 0;
	}

	for(i = 1; i < ((read1) + 1); i++)
	{
		for(f = 1; f < ((read) + 1); f ++)
		{
			if(line[f-1] == line2[i-1] )
			{
				if(c[f-1][i-1] == 0)
				{
					c[f][i] = 1;
				}
				else
				{
					c[f][i] = 1 + c[f-1][i-1];
				}
			}
			else
			{
				if(c[f][i-1] >= c[f-1][i])
				{
					c[f][i] = c[f][i-1];
				}
				else if(c[f][i-1] < c[f-1][i])
				{
					c[f][i] = c[f-1][i];
				}
				else
				{
					c[f][i] = 0;
				}
			}
		}
	}

	/*for(i = 0; i < ((read1) + 1); i++)
	{
		for(f = 1; f < ((read) + 1); f ++)
		{
			printf("%i ", c[f][i]);
		}
		printf("\n");
	}*/
if(true == 1)
{
	printf("LCS : ");
	find(c , (read), (read1), line, line2);
	printf("\n\n");
}
	return c[read][read1];
}
