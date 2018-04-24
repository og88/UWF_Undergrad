/*
HTTP Server program. This program handles request and sends appropriate file for those request. The program also checks for any errors and response apropriately to them
Omar Garcia
3/12/18
System and Networks II
*/

#include "Server.h"

int start(int port, int count);
int tokenRing(int tcp_server_socket);

int main(int argc, char *argv[])
{
	if (argc == 3)
	{
		printf("Program ran fine\n");
		printf("%s %s %s\n", argv[0], argv[1], argv[2]);
		start(atoi(argv[1]), atoi(argv[2]));
	}
	else
	{
		printf("Too few arguments\n");
	}
}

int start(int port, int count)
{
	int i;
	int portList[count];
	int clientList[count];
	for (i = 0; i < count; i++)
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
		tcp_server_address.sin_port = htons(port); //Passing the port number - in this case 60039
		tcp_server_address.sin_addr = ip;			//Connecting to 127.0.0.1

		// binding the socket to the IP address and port
		bind(tcp_server_socket, (struct sockaddr *)&tcp_server_address, sizeof(tcp_server_address)); //Params: which socket, cast for server address, its size																						 //listen for simultaneous connections
		listen(tcp_server_socket, count);																 //which socket, how many connections


		//Create a client socket
		int tcp_client_socket;
		tcp_client_socket = accept(tcp_server_socket, NULL, NULL); // server socket to interact with client, structure like before - if you know - else NULL for local connection
	//HTTP(tcp_server_socket);																	 //Call on the HTTP method

		char tcp_client_message[1024];
		read(tcp_client_socket, &tcp_client_message, sizeof(tcp_client_message)); // params: where (socket), what (string), how much - size of the server response, flags (0)
		printf("%s\n", tcp_client_message);

		char c;
		int i = 0, j = 0;
		char header[CHUNK];
		memset(header, 0, strlen(header));
		c = tcp_client_message[i];
		while (c != ';')
		{
			header[j] = c;
			j++;
			i++;
			c = tcp_client_message[i];
		}
		printf("%s\n", header);

		bool onList = false;


		if (strcmp(header, "insert") == 0)
		{
			i++;
			j = 0;
			char content[CHUNK];
			c = tcp_client_message[i];
			while (c != ';' && c != '\0' && c != ',')
			{
				content[j] = c;
				j++;
				content[j] = '\0';
				i++;
				c = tcp_client_message[i];
			}
			int j;
			for (j = 0; j < i;j++)
			{
				if (portList[j] == atoi(content))
				{
					onList = true;
				}
			}
			if (!onList)
			{
				portList[i] = atoi(content);
				clientList[i] = tcp_client_socket;
				write(tcp_client_socket, "true", strlen("true") + 1); // send where, what, how much, flags (optional)
			}
			else
			{
				write(tcp_client_socket, "false", strlen("false") + 1); // send where, what, how much, flags (optional)
				i--;
			}
		}
		else
		{
			i--;
		}
		printf("%i\n", onList);
		printf("client %i, server %i\n", tcp_client_socket, tcp_server_socket);
		close(tcp_server_socket); //Closes the connection to the server soccket
	}
	for (i = 0; i < count; i++)
	{
		if (i == (count - 1))
		{
			char message[512];
			if (i > 0)
			{
				sprintf(message, "next=%d;prev=%d;order=%i,%i;", portList[0], portList[i - 1], i, count);
			}
			else
			{
				sprintf(message, "next=%d;prev=%d;order=%i,%i;", portList[0], portList[0], i, count);
			}
				printf("%s\n", message);
			int len = strlen(message);
			message[len + 1] = '\0';
			send(clientList[i], message, strlen(message) + 1, 0); // send where, what, how much, flags (optional)
		}
		else if (i == 0)
		{
			/*char next[128];
			sprintf(next, "%d", portList[i+1]); // puts string into buffer
			printf("%s\n", next); // outputs so you can see it
			send(clientList[i], next, strlen(next), 0); // send where, what, how much, flags (optional)

			char previous[128];
			sprintf(previous, "%d", portList[count-1]); // puts string into buffer
			printf("%s\n", previous); // outputs so you can see it
			send(clientList[i], previous, strlen(previous), 0); // send where, what, how much, flags (optional)
			char order[16];
			sprintf(order,"%i",i);
			send(clientList[i], order, strlen(order), 0); // send where, what, how much, flags (optional)
			*/
			char message[512];
			sprintf(message, "next=%d;prev=%d;order=%i,%i;", portList[i + 1], portList[count - 1], i, count);
			printf("%s\n", message);
			int len = strlen(message);
			message[len + 1] = '\0';
			write(clientList[i], message, strlen(message) + 1); // send where, what, how much, flags (optional)

		}
		else if (i < (count - 1))
		{
			/*char next[128];
			sprintf(next, "%d", portList[i+1]); // puts string into buffer
			printf("%s\n", next); // outputs so you can see it
			send(clientList[i], next, strlen(next), 0); // send where, what, how much, flags (optional)

				char previous[128];
			sprintf(previous, "%d", portList[i-1]); // puts string into buffer
			printf("%s\n", previous); // outputs so you can see it
			send(clientList[i], previous, strlen(previous), 0); // send where, what, how much, flags (optional)
			char order[16];
			sprintf(order, "%i", i);
			send(clientList[i], order, strlen(order), 0); // send where, what, how much, flags (optional)
			char message[512];

			sprintf(message, "%s%s%s", next, previous, order);
			printf("%s\n", message);
			int len = strlen(message);
			message[len + 1] = '\0';
			*/

			char message[512];
			sprintf(message, "next=%d;prev=%d;order=%i,%i;", portList[i + 1], portList[i - 1], i, count);
			printf("%s\n", message);
			int len = strlen(message);
			message[len + 1] = '\0';
			send(clientList[i], message, strlen(message) + 1, 0); // send where, what, how much, flags (optional)

		}
		else
		{
			/*
		char next[128];
		sprintf(next, "%d", portList[0]); // puts string into buffer
		printf("%s\n", next); // outputs so you can see it
		send(clientList[i], next, strlen(next), 0); // send where, what, how much, flags (optional)

		char previous[128];
		sprintf(previous, "%d", portList[i-1]); // puts string into buffer
		printf("%s\n", previous); // outputs so you can see it
		send(clientList[i], previous, strlen(previous), 0); // send where, what, how much, flags (optional)
		char order[16];
		sprintf(order, "%i", i, count);
		send(clientList[i], order, strlen(order), 0); // send where, what, how much, flags (optional)

		char message[512];
		sprintf(message, "%s%s%s",next,previous,order);
		printf("%s\n", message);
		int len = strlen(message);
		message[len + 1] = '\0';*/

		}
	}
	return 0;
}

