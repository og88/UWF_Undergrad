/*
HTTP Server program. This program handles request and sends appropriate file for those request. The program also checks for any errors and response apropriately to them
Omar Garcia
3/12/18
System and Networks II
*/

#include "Server.h"

int main()
{
	//create the server socket
	int tcp_server_socket;								 //variable for the socket descriptor
	tcp_server_socket = socket(AF_INET, SOCK_STREAM, 0); //calling the socket function. Params: Domain of the socket (Internet in this case), type of socket stream (TCP), Protocol (default, 0 for TCP)

	//Define an ip adress
	struct in_addr ip;
	if (inet_pton(AF_INET, "127.0.0.1", &ip) != 1)
	{
		perror("inet_pton"); //printout error if ip address could not be found
		exit(1);
	}

	//define the server address
	struct sockaddr_in tcp_server_address;		//declaring a structure for the address
	tcp_server_address.sin_family = AF_INET;	//Structure Fields' definition: Sets the address family of the address the client would connect to
	tcp_server_address.sin_port = htons(60039); //Passing the port number - in this case 60039
	tcp_server_address.sin_addr = ip;			//Connecting to 127.0.0.1

	// binding the socket to the IP address and port
	bind(tcp_server_socket, (struct sockaddr *)&tcp_server_address, sizeof(tcp_server_address)); //Params: which socket, cast for server address, its size																						 //listen for simultaneous connections
	listen(tcp_server_socket, 5);																 //which socket, how many connections
	HTTP(tcp_server_socket);																	 //Call on the HTTP method

	close(tcp_server_socket); //Closes the connection to the server soccket

	return 0;
}

int HTTP(int tcp_server_socket)
{
	int kill = 0;	 //Kill is used to determine if the server connection should terminate
	while (kill != 1) //loop lets the server listen to multiple request per instance
	{
		char tcp_client_message[1024];

		//Create a client socket
		int tcp_client_socket;
		tcp_client_socket = accept(tcp_server_socket, NULL, NULL); // server socket to interact with client, structure like before - if you know - else NULL for local connection

		//Read the request of a client
		read(tcp_client_socket, &tcp_client_message, sizeof(tcp_client_message)); // params: where (socket), what (string), how much - size of the server response, flags (0)

		//Variables needed to handle requests
		char c;				 //Holds a char value. This is used to check for spaces and new-lines characters
		int i;				 //Variable used to keep track of our position of the variables used for HTTP headers
		int L = 0;			 //Variable used to keep track of the current position of the client message
		char header[CHUNK];  //Holds the entire HTTP header that will be broken down
		char command[CHUNK]; //Holds what command the client would like to run, for this project GET is the only available command
		char file[CHUNK];	//Holds the name of the file the client wants to retrieve
		char type[CHUNK];	//Type of connection

		c = tcp_client_message[L]; //c is set to the first character of the client message
								   //For loop to store the header into the header variable. Stops after new line or EOF.
		for (i = 0; c != EOF && c != '\n'; i++)
		{
			header[i] = c;
			L++;
			c = tcp_client_message[L];
		}
		L = 0;
		c = header[L];

		//Records the command
		for (i = 0; c > 32 && c < 127; i++)
		{
			command[i] = c;
			command[i + 1] = '\0';
			L++;
			c = header[L];
		}
		L++;
		c = header[L];

		//records the name of the file that needs to be retrieved
		for (i = 0; c > 32 && c < 127; i++)
		{
			file[i] = c;
			file[i + 1] = '\0';
			L++;
			c = header[L];
		}
		L++;
		c = header[L];

		//Records the type of conection
		for (i = 0; c > 32 && c < 127; i++)
		{
			type[i] = c;
			type[i + 1] = '\0';
			L++;
			c = header[L];
		}

		//Calls the appropriate command from the user requests
		if (strcmp(command, "GET") == 0)
		{
			//return item determines whether the code should run again
			kill = GET(tcp_client_socket, file, type);
		}
		//closes connection with client
		close(tcp_client_socket);
	}
	return 0;
}

int GET(int tcp_client_socket, char file[], char *type)
{

	//Open defualt page
	if (strcmp(file, "/") == 0)
	{
		char tcp_server_message[CHUNK] = "HTTP/1.1 200 OK\r\n"; //Basic HTTP message
		char *fle = "Main.html";
		FILE *f;
		f = fopen(fle, "r"); //Open in read only

		fseek(f, 0, SEEK_END);													  //Go to end of file
		long input_file_size = ftell(f);										  //Find the length of the file
		rewind(f);																  //Return to the start of the file
		char *buffer;															  //Holds the file contents
		buffer = malloc(input_file_size * (sizeof(char)));						  //Assigns the apropriate size for the buffer
		fread(buffer, sizeof(char), input_file_size, f);						  //Copy the contents of the file to a buffer
		fclose(f);																  //Close file
		strcat(tcp_server_message, buffer);										  //Add the html file to the header
		write(tcp_client_socket, tcp_server_message, strlen(tcp_server_message)); //Send the header and html together
		return 0;
	}
	//Terminate server
	else if (strcmp(file, "/KILL") == 0)
	{
		char tcp_server_message[CHUNK] = "HTTP/1.1 001 ShutDown\r\n";			  //Basic HTTP message
		write(tcp_client_socket, tcp_server_message, strlen(tcp_server_message)); //Send the header and html together
		printf("User requested to exit \n\n");
		return 1;
	}
	//Send a requested file
	else
	{
		char tcp_server_message[CHUNK] = "HTTP/1.1 200 OK\r\n"; //Basic HTTP message
		char c = file[1];										//Name of directory
		char fle[128];											//Entire name of file, without '/'
		int i;

		//Adds the name of the file to fle
		for (i = 0; c > 32 && c < 127; i++)
		{
			fle[i] = c;
			fle[i + 1] = '\0';
			c = file[i + 2];
		}

		//Lets user know what is being requested from the server
		printf("Requesting file %s\n", fle);

		//Open the file to be sent
		FILE *f;
		f = fopen(fle, "r");
		//Error handler for opening the file
		if (f == NULL)
		{
			write(tcp_client_socket, "HTTP/1.1 404 not found\n\r", 128);
			printf("Error\n");
		}
		else //If all is good, proceed
		{
			fseek(f, 0, SEEK_END);													  //Go to end of file
			long input_file_size = ftell(f);										  //Find the length of the file
			rewind(f);																  //Return to the start of the file
			char *buffer;															  //Holds the file contents
			buffer = malloc(input_file_size * (sizeof(char)));						  //Assigns the apropriate size for the buffer
			fread(buffer, sizeof(char), input_file_size, f);						  //Copy contnets of file to buffer
			fclose(f);																  //Close file
			strcat(tcp_server_message, buffer);										  //add html to the message
			write(tcp_client_socket, tcp_server_message, strlen(tcp_server_message)); //write the message to the client
		}
		return 0;
	}
	return 0;
}
