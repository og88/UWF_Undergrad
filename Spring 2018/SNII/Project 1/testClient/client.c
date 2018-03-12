#include<stdio.h>
#include<sys/types.h>
#include<string.h>
#include<stdlib.h>
#include<sys/socket.h>
#include<arpa/inet.h>
#include<unistd.h>
#define SA struct sockaddr
int main(int argc,char**argv)
{
int sockfd;
char fname[25];
int len;
struct sockaddr_in servaddr,cliaddr;
sockfd=socket(AF_INET,SOCK_STREAM,0);
bzero(&servaddr,sizeof(servaddr));
servaddr.sin_family=AF_INET;
servaddr.sin_addr.s_addr=htonl(INADDR_ANY);
servaddr.sin_port=htons(atoi(argv[1]));
inet_pton(AF_INET,argv[1],&servaddr.sin_addr);
connect(sockfd,(SA*)&servaddr,sizeof(servaddr));

FILE *f;
f=fopen("Test.html","r");
fseek(f, 0, SEEK_END);
long input_file_size = ftell(f);
rewind(f);
char * buffer;
buffer = malloc(input_file_size * (sizeof(char)));
fread(buffer, sizeof(char), input_file_size, f);
fclose(f);
printf("%s", buffer);
write(sockfd,buffer,10000);
printf("the file was sent successfully");
}