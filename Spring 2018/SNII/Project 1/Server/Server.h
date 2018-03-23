/*
HTTP Server program
Omar Garcia
3/17/18
*/



#ifndef _Sever_h
#define _Server_h

#include <stdio.h>		//Standard library
#include <stdlib.h>		//Standard library
#include <string.h>		//Library for string operations
#include <sys/socket.h> //API and definitions for the sockets
#include <sys/types.h>  //more definitions
#include <netinet/in.h> //Structures to store address information
#include <unistd.h>
#include <arpa/inet.h>
#include <netdb.h> 

#define CHUNK 512

/*Method that handles the interaction between the client and the server*/
int HTTP(int tcp_server_socket);

/*Method for handling get requests*/
int GET(int tcp_client_socket, char file[], char *type);

#endif