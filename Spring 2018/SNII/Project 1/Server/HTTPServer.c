
//TCP Server
// Create Socket > bind the IP and port for the socket > listen on port > accept connection > send data > close socket

#include <stdio.h>          //Standard library
#include <stdlib.h>         //Standard library
#include <string.h>
#include <sys/socket.h>     //API and definitions for the sockets
#include <sys/types.h>      //more definitions
#include <netinet/in.h>     //Structures to store address information
#include <unistd.h>
#include<arpa/inet.h>
#include<netdb.h>

int main() {

	char tcp_server_message[256] = "HTTP/1.1 200 OK\r\n";
	char tcp_client_message[256];

	//create the server socket
	int tcp_server_socket;                                  //variable for the socket descriptor
	tcp_server_socket = socket(AF_INET, SOCK_STREAM, 0);    //calling the socket function. Params: Domain of the socket (Internet in this case), type of socket stream (TCP), Protocol (default, 0 for TCP)
	struct in_addr ip;
	if(inet_pton(AF_INET,"127.0.0.1",&ip)!=1)
	{
        perror("inet_pton");
        exit(1);
    }
															//define the server address
	struct sockaddr_in tcp_server_address;                  //declaring a structure for the address
	tcp_server_address.sin_family = AF_INET;                //Structure Fields' definition: Sets the address family of the address the client would connect to
	tcp_server_address.sin_port = htons(60039);             //Passing the port number - converting in right network byte order
	tcp_server_address.sin_addr = ip;        //Connecting to 0.0.0.0

															// binding the socket to the IP address and port
	bind(tcp_server_socket, (struct sockaddr *) &tcp_server_address, sizeof(tcp_server_address));  //Params: which socket, cast for server address, its size

																								   //listen for simultaneous connections
	listen(tcp_server_socket, 5);  //which socket, how many connections

	int tcp_client_socket;
	tcp_client_socket = accept(tcp_server_socket, NULL, NULL);  // server socket to interact with client, structure like before - if you know - else NULL for local connection

																//send data stream
	//send(tcp_client_socket, tcp_server_message, sizeof(tcp_server_message), 0);  // send where, what, how much, flags (optional)
	recv(tcp_client_socket, &tcp_client_message, sizeof(tcp_client_message), 0);   // params: where (socket), what (string), how much - size of the server response, flags (0)
		printf("%s", tcp_client_message);

	//send(tcp_client_socket, tcp_server_message, sizeof(tcp_server_message), 0);  // send where, what, how much, flags (optional)
	FILE *f;
f=fopen("Test.html","r");
fseek(f, 0, SEEK_END);
long input_file_size = ftell(f);
rewind(f);
char * buffer;
buffer = malloc(input_file_size * (sizeof(char)));
fread(buffer, sizeof(char), input_file_size, f);
fclose(f);
strcat(tcp_server_message, buffer);
write(tcp_client_socket,tcp_server_message,input_file_size);
																					 //Output, as received from Server
	//printf("%s", tcp_server_message);

																				 //close the socket
	close(tcp_server_socket);

	return 0;
}
