/*
HTTP Client program. Created to request files from an HTTP server. The program asks user for the server ip adress and port number. Then for their desired file.
Omar Garcia
3/12/18
System and Networks II
*/

#include "Client.h"
#include "fileHandler.c"

int start(int serverPort, char *clientPort);
int userOrder(int nextPort, int previousPort, int order);
int sendConnection(int port);
int recieveConnection(int port);
int userActions(int tcp_client_socket);


char* fileName;
char* ID;

int main(int argc, char *argv[])
{
	if (argc == 5)
	{
		printf("Program ran fine\n");
		printf("%s %s %s %s %s\n", argv[0], argv[1], argv[2], argv[3], argv[4]);
		fileName = argv[4];
		ID = argv[3];
		start(atoi(argv[2]), argv[3]);
	}
	else
	{
		printf("Too few arguments\n");
	}
}

int start(int serverPort, char *clientPort)
{
	char ipAdress[CHUNK] = "127.0.0.1"; //String to hold IP address

		//creating the TCP socket
	int tcp_client_socket;								 //Socket descriptor
	tcp_client_socket = socket(AF_INET, SOCK_STREAM, 0); //Calling the socket function - args: socket domain, socket stream type, TCP protocol (default)

	//Check whether the connectio to the ip address is valid
	struct in_addr ip;
	if (inet_pton(AF_INET, ipAdress, &ip) != 1)
	{
		perror("inet_pton");
		exit(1);
	}

	//specify address and port of the remote socket
	struct sockaddr_in tcp_server_address;				//declaring a structure for the address
	tcp_server_address.sin_family = AF_INET;			//Structure Fields' definition: Sets the address family of the address the client would connect to
	tcp_server_address.sin_port = htons(serverPort); //Specify and pass the port number to connect - converting in right network byte order
	tcp_server_address.sin_addr = ip;			//Connecting to 127.0.0.1

	//connecting to the remote socket
	int connection_status = connect(tcp_client_socket, (struct sockaddr *)&tcp_server_address, sizeof(tcp_server_address)); //params: which socket, cast for address to the specific structure type, size of address
	if (connection_status == -1)																							//return value of 0 means all okay, -1 means a problem
	{
		printf(" Problem connecting to the socket! Sorry!! \n");
	}
	else
	{
		//Start loop to recieve multiple requests from the client
		//kill = http(tcp_client_socket);
		printf("Connected \n");
		send(tcp_client_socket, clientPort, strlen(clientPort), 0); // send where, what, how much, flags (optional)
	}

	char tcp_server_message[1024];
	read(tcp_client_socket, &tcp_server_message, sizeof(tcp_server_message)); // params: where (socket), what (string), how much - size of the server response, flags (0)
	printf("%s\n", tcp_server_message);
	if (strcmp(tcp_server_message, "true") == 0)
	{
		printf("valid\n");
	}
	else
	{
		printf("port number is already taken\n");
	}

	read(tcp_client_socket, &tcp_server_message, sizeof(tcp_server_message)); // params: where (socket), what (string), how much - size of the server response, flags (0)
	printf("%s\n", tcp_server_message);
	int nextPort = atoi(tcp_server_message);
	memset(tcp_server_message, 0, strlen(tcp_server_message));

	read(tcp_client_socket, &tcp_server_message, sizeof(tcp_server_message)); // params: where (socket), what (string), how much - size of the server response, flags (0)
	printf("%s\n", tcp_server_message);
	int previousPort = atoi(tcp_server_message);
	memset(tcp_server_message, 0, strlen(tcp_server_message));

	read(tcp_client_socket, &tcp_server_message, sizeof(tcp_server_message)); // params: where (socket), what (string), how much - size of the server response, flags (0)
	printf("%s\n", tcp_server_message);
	int order = atoi(tcp_server_message);
	memset(tcp_server_message, 0, strlen(tcp_server_message));

	printf("nextPort %i, previousPort %i, order %i\n", nextPort, previousPort, order);
	close(tcp_client_socket);
	userOrder(nextPort, previousPort, order);
	return 0;
}

int userOrder(int nextPort, int previousPort, int order)
{
	int finish = 0;
	if (order == 0)
	{
		printf("First\n");
		while (finish == 0)
		{
			finish = sendConnection(nextPort);
			if (order == 0)
			{
				finish = recieveConnection(previousPort);
			}
		}
	}
	else
	{
		while (finish == 0)
		{
			finish = recieveConnection(previousPort);
			if (order == 0)
			{
				finish = sendConnection(nextPort);
			}
		}
	}
}


int sendConnection(int port)
{
	char ipAdress[CHUNK] = "127.0.0.1"; //String to hold IP address

		//creating the TCP socket
	int tcp_client_socket;								 //Socket descriptor
	tcp_client_socket = socket(AF_INET, SOCK_STREAM, 0); //Calling the socket function - args: socket domain, socket stream type, TCP protocol (default)

	//Check whether the connectio to the ip address is valid
	struct in_addr ip;
	if (inet_pton(AF_INET, ipAdress, &ip) != 1)
	{
		perror("inet_pton");
		exit(1);
	}

	//specify address and port of the remote socket
	struct sockaddr_in tcp_server_address;				//declaring a structure for the address
	tcp_server_address.sin_family = AF_INET;			//Structure Fields' definition: Sets the address family of the address the client would connect to
	tcp_server_address.sin_port = htons(port); //Specify and pass the port number to connect - converting in right network byte order
	tcp_server_address.sin_addr = ip;			//Connecting to 127.0.0.1

	//connecting to the remote socket
	int connection_status = connect(tcp_client_socket, (struct sockaddr *)&tcp_server_address, sizeof(tcp_server_address)); //params: which socket, cast for address to the specific structure type, size of address
	if (connection_status == -1)																							//return value of 0 means all okay, -1 means a problem
	{
		printf(" Problem connecting to the socket! Sorry!! \n");
		return 1;
	}
	else
	{
		printf("Connected \n");
		int result = userActions(tcp_client_socket);
		close(tcp_client_socket);
		return result;
	}
	close(tcp_client_socket);
}

int userActions(int tcp_client_socket)
{
	printf("Please make a selection\n1. Read\n2. Write\n3. List\n4. Exit\n");
	int selection;
	bool done = false;
	while (!done)
	{

		scanf("%d", &selection);
		if (selection == 1) //Read
		{
			openFile(fileName, "r");
			long filesize = fileSize();
			char* fileContent = malloc(filesize);
			fileContent = readFile();
			printf("%s\n", fileContent);
			closeFile();
			send(tcp_client_socket, fileContent, filesize, 0); // send where, what, how much, flags (optional)
			done = true;
			return 0;
		}
		else if (selection == 2) //write
		{
			getchar();
			openFile(fileName,"a+");
			char buff[128];
			scanf("%[^\n]s", &buff);
			char message[256];
			sprintf(message, "<message n = %s> %s </message>\n",ID, buff);
			writeToFile(message);
			long filesize = fileSize();
			char* fileContent = malloc(filesize);
			fileContent = readFile();
			closeFile();
			send(tcp_client_socket, fileContent, filesize, 0); // send where, what, how much, flags (optional)
			done = true;
			return 0;
		}
		else if (selection == 3) //List
		{
			done = true;
			return 0;
		}
		else if (selection == 4) //Exit
		{
			send(tcp_client_socket, "exit", sizeof("exit"), 0); // send where, what, how much, flags (optional)
			done = true;
			return 1;
		}
	}
	return 0;
}

int recieveConnection(int port)
{
	char ipAdress[CHUNK] = "127.0.0.1"; //String to hold IP address

		//creating the TCP socket
	int tcp_client_socket;								 //Socket descriptor
	tcp_client_socket = socket(AF_INET, SOCK_STREAM, 0); //Calling the socket function - args: socket domain, socket stream type, TCP protocol (default)

	//Check whether the connectio to the ip address is valid
	struct in_addr ip;
	if (inet_pton(AF_INET, ipAdress, &ip) != 1)
	{
		perror("inet_pton");
		exit(1);
	}

	//specify address and port of the remote socket
	struct sockaddr_in tcp_server_address;				//declaring a structure for the address
	tcp_server_address.sin_family = AF_INET;			//Structure Fields' definition: Sets the address family of the address the client would connect to
	tcp_server_address.sin_port = htons(port); //Specify and pass the port number to connect - converting in right network byte order
	tcp_server_address.sin_addr = ip;			//Connecting to 127.0.0.1

	// binding the socket to the IP address and port
	bind(tcp_client_socket, (struct sockaddr *)&tcp_server_address, sizeof(tcp_server_address)); //Params: which socket, cast for server address, its size																						 //listen for simultaneous connections
	listen(tcp_client_socket, 1);

	//Create a client socket
	int tcp_preClient_socket;
	tcp_preClient_socket = accept(tcp_client_socket, NULL, NULL); // server socket to interact with client, structure like before - if you know - else NULL for local connection

	char tcp_client_message[512];
	read(tcp_preClient_socket, &tcp_client_message, sizeof(tcp_client_message)); // params: where (socket), what (string), how much - size of the server response, flags (0)
	printf("client Message : %s\n", tcp_client_message);

	close(tcp_client_socket);
	if(strcmp(tcp_client_message, "exit"))
	{
		return 1;
	}
	return 0;
}
