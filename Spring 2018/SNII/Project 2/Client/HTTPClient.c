/*
HTTP Client program. Created to request files from an HTTP server. The program asks user for the server ip adress and port number. Then for their desired file.
Omar Garcia
3/12/18
System and Networks II
*/

#include "Client.h"

int main()
{

	char ipAdress[CHUNK] = "127.0.0.1"; //String to hold IP address
	char portNum[CHUNK] = "60039";		//String to hold the port number
	char response[CHUNK];				//String to hold the users response

	//Ask user for the ip address the would like to connect to
	printf("Please input server address: EX: 127.0.0.1\n");
	scanf("%s", response);
	strcpy(ipAdress, response);
	strcat(ipAdress, "\0");

	//Ask the user for the port number they would lke to connect to
	printf("Please input server port nmuber: EX: 60039\n");
	scanf("%s", response);
	strcpy(portNum, response);
	strcat(portNum, "\0");

	int kill = 0;
	while (kill == 0)
	{
		//creating the TCP socket
		int tcp_client_socket;								 //Socket descriptor
		tcp_client_socket = socket(AF_INET, SOCK_STREAM, 0); //Calling the socket function - args: socket domain, socket stream type, TCP protocol (default)

		//Check whether the connectio to the ip address is valid
		struct in_addr ip;
		if (inet_pton(AF_INET, ipAdress, &ip) != 1)
		{
			perror("inet_pton");
			exit(1);
			kill = 1;
		}

		//specify address and port of the remote socket
		struct sockaddr_in tcp_server_address;				//declaring a structure for the address
		tcp_server_address.sin_family = AF_INET;			//Structure Fields' definition: Sets the address family of the address the client would connect to
		tcp_server_address.sin_port = htons(atoi(portNum)); //Specify and pass the port number to connect - converting in right network byte order
		tcp_server_address.sin_addr.s_addr = INADDR_ANY;	//Connecting to 0.0.0.0

		//connecting to the remote socket
		int connection_status = connect(tcp_client_socket, (struct sockaddr *)&tcp_server_address, sizeof(tcp_server_address)); //params: which socket, cast for address to the specific structure type, size of address
		if (connection_status == -1)																							//return value of 0 means all okay, -1 means a problem
		{
			printf(" Problem connecting to the socket! Sorry!! \n");
		}
		else
		{
			//Start loop to recieve multiple requests from the client
			kill = http(tcp_client_socket);
		}
	}
	return 0;
}

int http(int tcp_client_socket)
{
	char fileName[CHUNK] = "/";		 //Holds the name of the file requested
	char *command = "GET ";			 //Command for the server, in this case GET
	char *httpVer = " HTTP/1.1\r\n"; //HTTP version 1.1
	char response[CHUNK];			 //String to hold servers response

	int i = 0;
	int l = 0;
	char c;

	printf("Please input file name. EX: Main.html or KILL to exit\n");
	scanf("%s", response);
	strcat(fileName, response);

	char tcp_server_response[CHUNK * 2];

	//Create a HTTP header
	char *header = malloc(strlen(fileName) + strlen(command) + strlen(httpVer));
	strcat(header, command);
	strcat(header, fileName);
	strcat(header, httpVer);
	send(tcp_client_socket, header, strlen(header), 0); // send where, what, how much, flags (optional)
	printf("%s\n", header);
	free(header);
	read(tcp_client_socket, tcp_server_response, CHUNK * 2); //read response form the serer
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

	//Checks to see if HTTP version is currect
	if (strcmp(Version, "HTTP/1.1") == 0)
	{
		return codeHandler(l, tcp_server_response, response);
	}
	//return error if  not
	else
	{
		printf("Incorrect version of HTTP\n");
		return 1;
	}
	close(tcp_client_socket); //Close conection with server
	return 0;
}

int codeHandler(int l, char *tcp_server_response, char *fileName)
{
	char Code[256] = "";
	int i;
	char c;
	c = tcp_server_response[l];
	l++;
	//Copy the code sent by the server 200 ok, 404 file is not found
	for (i = 0; c != ' '; i++)
	{
		Code[i] = c;
		Code[i + 1] = '\0';
		c = tcp_server_response[l];
		l++;
	}
	//Checks to see if request was handled currectly
	if (strcmp(Code, "200") == 0)
	{
		return fileHandler(l, tcp_server_response, fileName);
	}
	else if(strcmp(Code, "001") == 0)
	{
		printf("Shutting Down\n");
		return 1;
	}
	else //Print error for user to see
	{
		for (i; c != '\n' && c != '\r'; i++)
		{
			Code[i] = c;
			Code[i + 1] = '\0';
			c = tcp_server_response[l];
			l++;
		}
		printf("Error: %s\n", Code);
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
	//Records the status message sent by the server
	for (i = 0; c != ' ' && c != '\n' && c != '\r'; i++)
	{
		Status[i] = c;
		Status[i + 1] = '\0';
		c = tcp_server_response[l];
		l++;
	}
	l++;
	//Checks to see if the request was handled ok
	if (strcmp(Status, "OK") == 0)
	{
		printf("Status is OK\n");
		char fileContents[CHUNK * 4] = "";
		c = tcp_server_response[l];
		l++;
		//records the contents of the requested file
		for (i = 0; c != '\0'; i++)
		{
			fileContents[i] = c;
			fileContents[i + 1] = '\0';
			c = tcp_server_response[l];
			l++;
		}
		//Creates the file requested by the user
		FILE *fp;
		fp = fopen(fileName, "w");
		fprintf(fp, "%s", fileContents);
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