#include <stdio.h>
#include <string.h>

FILE *fp;

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
	fseek(fp, 0, SEEK_END);
	long fsize = ftell(fp);
	fseek(fp, 0, SEEK_SET); //same as rewind(f);

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

int writeToFile(char *string)
{
	int CHUNK = 256;
	if (fp == NULL)
	{
		perror("Error opening file.");
		return 0;
	}
	else
	{
		char message[CHUNK];
		fseek(fp, 0, SEEK_END);
		long fsize = ftell(fp);
		printf("%i\n",fsize);
		fseek(fp, fsize, SEEK_SET);
		sprintf(message, "<message n = %i> %s </message>", fsize / CHUNK, string);
		fputs(message, fp);
		fseek(fp, (fsize + CHUNK) - 1, SEEK_SET);
		fputs("\n", fp);
		return 1;
	}
}

char *listMessage(int x)
{
		int CHUNK = 256;
	fseek(fp, 0, SEEK_END);
	int fsize = ftell(fp);
	if ((x * CHUNK) <= fsize)
	{
		fseek(fp, CHUNK * 4, SEEK_SET);
		char *string = malloc(fsize + 1);
		fread(string, CHUNK, 1, fp);
		printf("%s\n", string);
		return string;
	}
	else
	{
		perror("Message #%i has not been add");
		return "";
	}
	return "";
}
