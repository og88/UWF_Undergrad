
//Basic TCP Client: socket() creation > connect() > receive() > display > close

#include <stdio.h>		//Standard library
#include <stdlib.h>		//Standard library
#include <sys/socket.h> //API and definitions for the sockets
#include <sys/types.h>  //more definitions
#include <netinet/in.h> //Structures to store address information
#include <unistd.h>
#include <string.h>     //Library for strings
#include <arpa/inet.h>

#define CHUNK 256

/**/
int http(int tcp_client_socket);
int codeHandler(int l, char *tcp_server_response, char *fileName);
int fileHandler(int l, char *tcp_server_response, char *fileName);

int main()
{

	char ipAdress[CHUNK] = "127.0.0.1"; //String to hold IP address
	char portNum[CHUNK] = "60039";      //String to hold the port number
	char response[CHUNK];               //String to hold the users response

	printf("Please input server address: EX: 127.0.0.1\n");
	scanf("%s", response);
	strcpy(ipAdress, response);
	strcat(ipAdress, "\0");
	printf("Please input server port nmuber: EX: 60039\n");
	scanf("%s", response);
	strcpy(portNum, response);
	strcat(portNum, "\0");

	//creating the TCP socket
	int tcp_client_socket;								 //Socket descriptor
	tcp_client_socket = socket(AF_INET, SOCK_STREAM, 0); //Calling the socket function - args: socket domain, socket stream type, TCP protocol (default)

	struct in_addr ip;
	if (inet_pton(AF_INET, ipAdress, &ip) != 1)
	{
		perror("inet_pton");
		exit(1);
	}

	//specify address and port of the remote socket
	struct sockaddr_in tcp_server_address;				//declaring a structure for the address
	tcp_server_address.sin_family = AF_INET;			//Structure Fields' definition: Sets the address family of the address the client would connect to
	tcp_server_address.sin_port = htons(atoi(portNum)); //Specify and pass the port number to connect - converting in right network byte order
	tcp_server_address.sin_addr.s_addr = INADDR_ANY;	//Connecting to 0.0.0.0

	//connecting to the remote socket
	int connection_status = connect(tcp_client_socket, (struct sockaddr *)&tcp_server_address, sizeof(tcp_server_address)); //params: which socket, cast for address to the specific structure type, size of address
	if (connection_status == -1)
	{ //return value of 0 means all okay, -1 means a problem
		printf(" Problem connecting to the socket! Sorry!! \n");
	}
	else
	{
		int kill = 0;
		while(kill == 0)
		{
		kill = http(tcp_client_socket);
		}
	}
	return 0;
}

int http(int tcp_client_socket)
{
	char fileName[CHUNK] = "/";
	char *command = "GET ";
	char *httpVer = " HTTP/1.1";
	char response[CHUNK];

	int i = 0;
	int l = 0;
	char c;

	printf("Please input file name. EX: Main.html\n");
	scanf("%s", response);
	strcat(fileName, response);

	char tcp_server_response[512];
	char *header = malloc(strlen(fileName) + strlen(command) + strlen(httpVer));
	strcat(header, command);
	strcat(header, fileName);
	strcat(header, httpVer);
	send(tcp_client_socket, header, strlen(header), 0); // send where, what, how much, flags (optional)

	read(tcp_client_socket, tcp_server_response, 512);
	c = tcp_server_response[0];
	l = 1;
	char Version[CHUNK];
	for (i = 0; c != ' '; i++)
	{
		Version[i] = c;
		Version[i + 1] = '\0';
		c = tcp_server_response[l];
		l++;
	}
	printf("%s\n", Version);

	if (strcmp(Version, "HTTP/1.1") == 0)
	{
		printf("Works\n");
		return codeHandler(l, tcp_server_response, response);
	}
	else
	{
		printf("Incorrect version of HTTP\n");
		return 1;
	}
	close(tcp_client_socket);
	return 0;
}

int codeHandler(int l, char *tcp_server_response, char * fileName)
{
	char Code[256] = "";
	int i;
	char c;
	c = tcp_server_response[l];
	l++;
	for (i = 0; c != ' '; i++)
	{
		Code[i] = c;
		Code[i + 1] = '\0';
		c = tcp_server_response[l];
		l++;
	}
	printf("%s\n", Code);
	if (strcmp(Code, "200") == 0)
	{
		printf("Good\n");
		return fileHandler(l, tcp_server_response, fileName);
	}
	else
	{
		printf("Bad\n");
		return 1;
	}
	return 0;
}

int fileHandler(int l, char *tcp_server_response, char *fileName)
{
	char Status[CHUNK] = "";
	char c = tcp_server_response[l];
	int i;
	l++;
	for (i = 0; c != ' ' && c != '\n' && c != '\r'; i++)
	{
		Status[i] = c;
		Status[i + 1] = '\0';
		c = tcp_server_response[l];
		l++;
	}
	l++;
	printf("%s\n", Status);
	if (strcmp(Status, "OK") == 0)
	{
		printf("Status is OK\n");
		char file[CHUNK * 4] = "";
		c = tcp_server_response[l];
		l++;
		for (i = 0; c != '\0'; i++)
		{
			file[i] = c;
			file[i + 1] = '\0';
			c = tcp_server_response[l];
			l++;
		}
		FILE *fp;
		fp = fopen(fileName, "w");
		fprintf(fp, "%s", file);
		printf("%s", file);
		fclose(fp);
		return 0;
	}
	else
	{
		printf("Error: Status is %s\n", Status);
		return 1;
	}
	return 0;
}