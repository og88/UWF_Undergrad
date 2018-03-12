
//Basic TCP Client: socket() creation > connect() > receive() > display > close

#include <stdio.h>          //Standard library
#include <stdlib.h>         //Standard library
#include <sys/socket.h>     //API and definitions for the sockets
#include <sys/types.h>      //more definitions
#include <netinet/in.h>     //Structures to store address information
#include <unistd.h>
#include <string.h>


int main() {
	char buff[256];
	char* header = "GET /Test.html HTTP/1.1\r\nHost: 127.0.0.1\r\n";

	//creating the TCP socket
	int tcp_client_socket;                                    //Socket descriptor
	tcp_client_socket = socket(AF_INET, SOCK_STREAM, 0);      //Calling the socket function - args: socket domain, socket stream type, TCP protocol (default)

															  //specify address and port of the remote socket
	struct sockaddr_in tcp_server_address;             //declaring a structure for the address
	tcp_server_address.sin_family = AF_INET;           //Structure Fields' definition: Sets the address family of the address the client would connect to
	tcp_server_address.sin_port = htons(39006);        //Specify and pass the port number to connect - converting in right network byte order
	tcp_server_address.sin_addr.s_addr = INADDR_ANY;   //Connecting to 0.0.0.0

													   //connecting to the remote socket
	int connection_status = connect(tcp_client_socket, (struct sockaddr *) &tcp_server_address, sizeof(tcp_server_address));     //params: which socket, cast for address to the specific structure type, size of address
	if (connection_status == -1) {                                                                                         //return value of 0 means all okay, -1 means a problem
		printf(" Problem connecting to the socket! Sorry!! \n");
	}
	char tcp_server_response[256];
	//recv(tcp_client_socket, &tcp_server_response, sizeof(tcp_server_response), 0);   // params: where (socket), what (string), how much - size of the server response, flags (0)

	send(tcp_client_socket, header, strlen(header), 0);  // send where, what, how much, flags (optional)
	//int byte_count = recv(tcp_client_socket, buff, sizeof(buff), 0);
	//buff[byte_count] = 0;
	
	
	FILE *fp;
	read(tcp_client_socket,buff, 256);
	fp = fopen("Test.html", "w");
	fprintf(fp,"%s",buff);
	printf("recv()'d %d bytes of data in buf\n",256);
	printf("%s",buff);

																					 //Output, as received from Server
	//printf("%s", tcp_server_response);

	//closing the socket
	close(tcp_client_socket);

	return 0;
}
