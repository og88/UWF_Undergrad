
//TCP Server
// Create Socket > bind the IP and port for the socket > listen on port > accept connection > send data > close socket

#include <stdio.h>  //Standard library
#include <stdlib.h> //Standard library
#include <string.h>
#include <sys/socket.h> //API and definitions for the sockets
#include <sys/types.h>  //more definitions
#include <netinet/in.h> //Structures to store address information
#include <unistd.h>
#include <arpa/inet.h>
#include <netdb.h>

int GET(int tcp_client_socket, char file[]);
int HTTP();

int main()
{
	HTTP();
}

int HTTP()
{
	char tcp_server_message[5140] = "HTTP/1.1 200 OK\r\n";
	char tcp_client_message[5140];

	int kill = 0;

	//create the server socket

	int tcp_server_socket;								 //variable for the socket descriptor
	tcp_server_socket = socket(AF_INET, SOCK_STREAM, 0); //calling the socket function. Params: Domain of the socket (Internet in this case), type of socket stream (TCP), Protocol (default, 0 for TCP)
	struct in_addr ip;
	if (inet_pton(AF_INET, "127.0.0.1", &ip) != 1)
	{
		perror("inet_pton");
		exit(1);
	}
	//define the server address
	struct sockaddr_in tcp_server_address;		//declaring a structure for the address
	tcp_server_address.sin_family = AF_INET;	//Structure Fields' definition: Sets the address family of the address the client would connect to
	tcp_server_address.sin_port = htons(60039); //Passing the port number - converting in right network byte order
	tcp_server_address.sin_addr = ip;			//Connecting to 0.0.0.0

	// binding the socket to the IP address and port
	bind(tcp_server_socket, (struct sockaddr *)&tcp_server_address, sizeof(tcp_server_address)); //Params: which socket, cast for server address, its size																						 //listen for simultaneous connections
	listen(tcp_server_socket, 5);
	while (kill != 1)
	{ //which socket, how many connections
		int tcp_client_socket;
		tcp_client_socket = accept(tcp_server_socket, NULL, NULL); // server socket to interact with client, structure like before - if you know - else NULL for local connection

		printf("##############\n");												  //send(tcp_client_socket, tcp_server_message, sizeof(tcp_server_message), 0);  // send where, what, how much, flags (optional)
		read(tcp_client_socket, &tcp_client_message, sizeof(tcp_client_message)); // params: where (socket), what (string), how much - size of the server response, flags (0)
		printf("%s\n", tcp_client_message);
		char c;
		int i;
		int L = 0;
		char header[256];
		char body[514];
		char command[256];
		char file[256];
		char type[256];
		c = tcp_client_message[L];
		for (i = 0; c != EOF && c != '\n'; i++)
		{
			header[i] = c;
			L++;
			c = tcp_client_message[L];
		}
		L++;
		c = tcp_client_message[L];
		for (i = 0; c != EOF; i++)
		{
			body[i] = c;
			L++;
			c = tcp_client_message[L];
		}
		L = 0;
		c = header[L];
		for (i = 0; c > 32 && c < 127; i++)
		{
			command[i] = c;
			command[i + 1] = '\0';
			L++;
			c = header[L];
		}

		L++;
		c = header[L];
		for (i = 0; c > 32 && c < 127; i++)
		{
			file[i] = c;
			file[i + 1] = '\0';
			L++;
			c = header[L];
		}
		printf("File == %s\n\n", file);
		L++;
		c = header[L];
		for (i = 0; c > 32 && c < 127; i++)
		{
			type[i] = c;
			type[i + 1] = '\0';
			L++;
			c = header[L];
		}

		if (strcmp(command, "GET") == 0)
		{
			kill = GET(tcp_client_socket, file);
		}
		close(tcp_client_socket);
	}
	//close the socket
	close(tcp_server_socket);

	return 0;
}

int GET(int tcp_client_socket, char file[])
{
	char tcp_server_message[256] = "HTTP/1.1 200 OK\r\n";
	if (strcmp(file, "/") == 0)
	{
		char *fle = "Main.html";
		FILE *f;
		f = fopen(fle, "r");
		fseek(f, 0, SEEK_END);
		long input_file_size = ftell(f);
		rewind(f);
		char *buffer;
		buffer = malloc(input_file_size * (sizeof(char)));
		fread(buffer, sizeof(char), input_file_size, f);
		fclose(f);
		strcat(tcp_server_message, buffer);
		write(tcp_client_socket, tcp_server_message, input_file_size);
		return 0;
	}
	else if(strcmp(file, "/KILL") == 0)
	{
return 1;
	}
	else
	{
		char c = file[1];
		char fle[514];
		int i;
		for (i = 0; c > 32 && c < 127; i++)
		{
			fle[i] = c;
			fle[i + 1] = '\0';
			c = file[i + 2];
		}
		printf("Requesting file %s\n", fle);

		FILE *f;
		f = fopen(fle, "r");
		if (f == NULL)
		{
			write(tcp_client_socket, "HTTP/1.1 404 not found\n\r", 128);
			printf("Error\n");
		}
		else
		{
			fseek(f, 0, SEEK_END);
			long input_file_size = ftell(f);
			rewind(f);
			char *buffer;
			buffer = malloc(input_file_size * (sizeof(char)));
			fread(buffer, sizeof(char), input_file_size, f);
			fclose(f);
			strcat(tcp_server_message, buffer);
			write(tcp_client_socket, tcp_server_message, input_file_size);
		}
		return 0;
	}
	return 0;
}
