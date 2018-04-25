#include <stdio.h>
#include <string.h>
#include <stdlib.h>


#define CHUNK 128

/*char nextPort[CHUNK];
char previousPort[CHUNK];*/

int nextPort;
int previousPort;
int order;
int size;

int intitialInfo(char* recieved)
{
	//char *recieved = "next=60039;prev=60038;order=1,3;";
	printf("%s\n", recieved);
	char buff[128];
	char c;
	int i = 0, j = 0;
	c = recieved[i];
	while (c != '=')
	{
		buff[j] = c;
		j++;
		i++;
		c = recieved[i];
	}
	i++;
	j = 0;
	if (strcmp(buff, "next") == 0)
	{
		c = recieved[i];
		char nPort[CHUNK];
		while (c != ';')
		{
			nPort[j] = c;
			j++;
			i++;
			c = recieved[i];
		}
		nextPort = atoi(nPort);
	}
	memset(buff, 0, strlen(buff));
	j = 0;
	i++;
	c = recieved[i];
	while (c != '=')
	{
		buff[j] = c;
		j++;
		i++;
		c = recieved[i];
	}
	i++;
	j = 0;
	if (strcmp(buff, "prev") == 0)
	{
		c = recieved[i];
		char pPort[CHUNK];
		while (c != ';')
		{
			pPort[j] = c;
			j++;
			i++;
			c = recieved[i];
		}
		previousPort = atoi(pPort);
	}
	memset(buff, 0, strlen(buff));
	j = 0;
	i++;
	c = recieved[i];
	while (c != '=')
	{
		buff[j] = c;
		j++;
		i++;
		c = recieved[i];
	}
	i++;
	j = 0;
	if (strcmp(buff, "order") == 0)
	{
		c = recieved[i];
		memset(buff, 0, strlen(buff));
		while (c != ';' && c != ',')
		{
			buff[j] = c;
			j++;
			i++;
			c = recieved[i];
		}
		order = atoi(buff);
		j = 0;
		i++;
		c = recieved[i];
		memset(buff, 0, strlen(buff));
		while (c != ';' && c != ',')
		{
				printf("%c\n", c);
			buff[j] = c;
			j++;
			i++;
			c = recieved[i];
		}
		printf("%s\n\n",buff);
		size = atoi(buff);
	}
	printf("%s\n", buff);


	printf("next port : %i\nPrevious port : %i\nnumber of connections : %i\nMy position : %i\n", nextPort, previousPort, size, order);
	return 0;
}