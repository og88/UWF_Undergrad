#include <stdio.h>

FILE *fp;

int openFile(char* fileName, char* type)
{
	if (fp == NULL)
	{
		fp = fopen(fileName, type);
	}
	else
	{
		printf("Error file already opened");
	}
}

int closeFile()
{
	if (fp != NULL)
	{
		fclose(fp);
		fp = NULL;
		return 1;
	}
	return 0;
}

char* readFile()
{
	fseek(fp, 0, SEEK_END);
	long fsize = ftell(fp);
	fseek(fp, 0, SEEK_SET);  //same as rewind(f);

	char *string = malloc(fsize + 1);
	fread(string, fsize, 1, fp);
	return string;
}

long fileSize()
{
	fseek(fp, 0, SEEK_END);
	long fsize = ftell(fp);
	return fsize;
}

int writeToFile(char* string)
{
	if (fp == NULL) {
		perror("Error opening file.");
		return 0;
	}
	else {
		fprintf(fp, string);
		return 1;
	}
}
