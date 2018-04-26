#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define CHUNK 256

int main()
{
  FILE *fp;
  fp = fopen("file.txt", "w+");
  char message[CHUNK];
int i;

  for (i = 0; i < 10; i++)
  {
    fseek(fp, 0, SEEK_END);
    long fsize = ftell(fp);
    fseek(fp, fsize, SEEK_SET);
    strcpy(message, "hello");
    char num[16];
    sprintf(num, "%i", i);
    strcat(message, num);
    fputs(message, fp);
    fseek(fp, (fsize + CHUNK) - 1, SEEK_SET);
    fputs("\n", fp);
  }

int x = 4;

    fseek(fp, 0, SEEK_END);
    int fsize = ftell(fp);
    if((x * CHUNK) <= fsize)
    {
    fseek(fp, CHUNK*4, SEEK_SET);
    char *string = malloc(fsize + 1);
	  fread(string, CHUNK, 1, fp);
    printf("%s\n", string);
    }
    else
    {
      perror("Message has not been add");
    }


  fclose(fp);

  return (0);
}