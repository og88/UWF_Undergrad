/*
 * cmdLineFunctions.h
 *
 *  Created on: Sep 21, 2017
 *      Author: omarg
 */

#ifndef CMDLINEFUNCTIONS_H_
#define CMDLINEFUNCTIONS_H_

typedef struct node
{
	char name[25];
	struct node* sib;
	struct node* child;
	int isFolder;
}node;

void ls(node* file);
void mkdir(char str[]);
void cd(char str[]);
void cdB();
void pwd();
void addF(char str[]);
void mv(char crt[],char str[]);
void cp(char crt[], char str[]);
void rm(char str[]);
node* findPre(node* previus, node* current, char str[]);
void whereIs(char str[]);
node* find(node* current, char str[]);
void check(node* folder, node* folder2);
void deleteNode(node* p);
void add(node* item);
void clone(node* crt, node* nw);

#endif /* CMDLINEFUNCTIONS_H_ */
