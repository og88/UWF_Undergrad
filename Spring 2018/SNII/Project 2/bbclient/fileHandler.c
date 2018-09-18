#include <stdio.h>
#include <string.h>

FILE *fp;

#define chunk 64

int openFile(char *fileName, char *type)
{
	if (fp == NULL)
	{
		fp = fopen(fileName, type);
		return 0;
	}
	else
	{
		printf("Error file already opened");
		return 1;
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

char *readFile()
{
	fseek(fp, 0, SEEK_SET); //same as rewind(f);
	fseek(fp, 0, SEEK_END);
	long fsize = ftell(fp);
	fseek(fp, 0, SEEK_SET); //same as rewind(f);
	char *string = malloc(fsize + 1);
	fread(string, fsize, 1, fp);
	fseek(fp, 0, SEEK_SET); //same as rewind(f);
	fflush(stdout);
	return string;
}

long fileSize()
{
	fseek(fp, 0, SEEK_END);
	long fsize = ftell(fp);
	return fsize;
}

int writeToFile(char *string)
{
	if (fp == NULL)
	{
		perror("Error opening file.");
		return 0;
	}
	else
	{
		char message[chunk];
		fseek(fp, 0, SEEK_SET); //same as rewind(f);
		fseek(fp, 0, SEEK_END);
		long fsize = ftell(fp);
		fseek(fp, fsize, SEEK_SET);
		sprintf(message, "<message n = %i> %s </message>\n", (fsize / chunk), string);
		fputs(message, fp);
		fseek(fp, (fsize + chunk) - 1, SEEK_SET);
		fputs("\n", fp);
		fseek(fp, 0, SEEK_SET); //same as rewind(f);
		return 1;
	}
}

char *listMessage(int x)
{
	fseek(fp, 0, SEEK_SET); //same as rewind(f);
	fseek(fp, 0, SEEK_END);
	int fsize = ftell(fp);
	if ((x * chunk) <= fsize)
	{
		fseek(fp, chunk * x, SEEK_SET);
		char *string = malloc(chunk*2);
		fread(string, chunk*2, 1, fp);
		printf("found message \n%s\n", string);
		fseek(fp, 0, SEEK_SET); //same as rewind(f);
		return string;
	}
	else
	{
		perror("Message #%i has not been add");
		return "";
	}
	return "";
}
