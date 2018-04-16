#ifndef _Client_h
#define _Client_h

#include <stdio.h>		//Standard library
#include <stdlib.h>		//Standard library
#include <sys/socket.h> //API and definitions for the sockets
#include <sys/types.h>  //more definitions
#include <netinet/in.h> //Structures to store address information
#include <unistd.h>
#include <string.h>     //Library for strings
#include <arpa/inet.h>


#define CHUNK 256

/*Handle sending a proper HTTP reqquest to a server*/
int http(int tcp_client_socket);
/*Handles code recieved from the server*/
int codeHandler(int l, char *tcp_server_response, char *fileName);
/*Handles creating the file requested by the user*/
int fileHandler(int l, char *tcp_server_response, char *fileName);
#endif