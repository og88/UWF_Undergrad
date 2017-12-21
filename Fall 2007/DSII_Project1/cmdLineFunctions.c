/*
 * Omar Garcia
 * cmdLineFunctions.c
 * Programming project 1
 *
 *
 *
 *
 * This file holds all the functions that are to be utilized by cmdLine.c
 * Each item is place in alphabetical order and there is a specific function for
 * creating each type addF for files and mkdir for directories.
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
 * This is the node struct initializer. The node contains four pieces of information
 *
 * @char name[] is a char array that holds the name of the folder/file.
 * @struct node* sib is a pointer node that points the the nodes sibling.
 * @struct node* child is a pointer node that points the the nodes child.
 * @int isFolder is an integer that is use by functions to determine if the node is  a folder.
 * If the node is a folder, the value of isForlder will be 1. This helps pwd() determine how it
 * should label the node F for file or D for directory. It also is used as  a safetynet to
 * prevent the user from changing directory to a file.
 */

typedef struct node
{
	char name[25];
	struct node* sib;
	struct node* child;
	int isFolder;
}node;
/* Used to specify the current directory the program should work on*/
int counter;

/* Used to determine if the node to be deleted is the child of the directory or a sibling of one of the other nodes*/
int isFirst;

/* Used as a counter in all other function, so it does not mess with the counter for the directories*/
int Counter2 = 0;

/* An activity log to keep track of the location
 * EX: /root/layer1/layer2/layer3 (current)     */
node* layer[15];

/* An activity log that keeps track of the location in the whereis command. keeps from messing with the overall activity log*/
node* whereis[15];

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/* lists all files and directories in the current directory,
 * indicating which (file or directory) it is.
 * Takes in the current directories first element and then recursively prints
 * out the siblings until it reaches a NULL sibling*/
void ls(node* file)
{
	if(file != NULL)
	{
		if(file->isFolder == 0)
		{
			printf(" F %s\n", file->name);
			ls(file->sib);
		}
		else
		{
			printf(" D %s\n", file->name);
			ls(file->sib);
		}
	}
}

/* creates a new directory if it does not already exist
 * Takes in the name of the new directory. The function sets the isFolder variable
 * to one to indicate that it is a directory/folder. The child and sibling are set to
 * NULL and the node is passed through to the add function to be properly added to
 * the current directory. If the director already exists, it will warn the user.*/
void mkdir(char str[])
{
	node* folder = NULL;
	folder = malloc(sizeof(node));
	if(folder == NULL)
	{
		printf("ERROR\n");
	}
	strcpy(folder->name, str);
	folder->isFolder = 1;
	folder->sib = NULL;
	folder->child = NULL;
	printf("%s\n",str);
	add(folder);
}

/* changes into specified directory if it exists and if it is a directory.
 * The function increments the counter variable by one and adds the directory
 * requested to the activity log layer. It then calls pwd() to show the user
 * the new directory*/
void cd(char str[])
{
	node* folder = find(layer[counter],str);
	if(folder != NULL)
	{
		if(folder->isFolder == 0)
		{
			printf("ERROR: can't change directory to a file\n");
		}
		else
		{
			counter++;
			layer[counter] = folder;
		}
		pwd();
	}
	else
	{
		printf("ERROR: %s is not in %s\n",str,layer[counter]->name);
	}
}

/*changes to the parent directory. This done by subtracting one on the activity log layer
 * the previous directory will remain in the activity log until it is over written by the cd command.
 * The previous directory must not be free() since it is a pointer and will delete it from any
 * other node pointing to it.*/
void cdB()
{
	if(counter != 0)
	{
		counter--;
		pwd();
	}
	else
	{
		printf("Can't go back any further\n");
	}
}

/* specifies the current directory as: root/<yourname>/nextdir/etc/. Simple prints out the activity log with a / in front.*/
void pwd()
{
	int x;
	printf("ROOT/");
	for(x = 0; x < counter + 1; x++)
	{
		printf("%s/",layer[x]->name);
	}
	printf("\n");
}

/*adds a file to the current directory. Works very similar to the mkdir command. The only difference is that isFolder is set to 0*/
void addF(char str[])
{
	node* file = NULL;
	file = malloc(sizeof(node));
	if(file == NULL)
	{
		printf("ERROR\n");
	}
	strcpy(file->name, str);
	file->isFolder = 0;
	file->sib = NULL;
	file->child = NULL;
	add(file);
}

/* change the name of the file or directory to the new name. To accomplish this, the function first calls
 * on the find function to determine whether the requested item exist. If it exists, the function creates a new
 * node and calls either the addF or mkdir function to make the new item. The function to use is determined based on what the old
 * nodes isFolder variable was. The function simple passes the new name into the specific function. After the new node has been
 * succesfully added, the function calls rm() to remove the old node. */
void mv(char crt[], char str[])
{
	node* old = find(layer[counter], crt);
	if(old != NULL)
	{
		if(old->isFolder == 1)
		{
			mkdir(str);
		}
		else
		{
			addF(str);
		}
		rm(old->name);
	}
	else
	{
		printf("%s not found in %s\n", crt, layer[counter]->name);
	}


}

/* copy file or folder to the new name. This function creates a deep copy of a node. It does not set node1 = node2. This will
 * set the pointer in node2 = to those in node1. This will cause anything to happen in node1 to happen in node2.
 * Instead, the function calls on another function clone() to create brand new nodes for node2 each with
 * the values copied in them, not the pointers.*/
void cp(char crt[], char str[])
{
	node* current = find(layer[counter],crt);
	if(current != NULL)
	{
		node* new = malloc(sizeof(node));
		strcpy(new->name, str);
		new->sib = NULL;
		new->child = NULL;
		new->isFolder = current->isFolder;
		add(new);
		if(current->child != NULL)
		{
			node* newChild = malloc(sizeof(node));
			strcpy(newChild->name,current->child->name);
			newChild->isFolder = current->child->isFolder;
			newChild->sib = NULL;
			newChild->child = NULL;
			new->child = newChild;
			clone(current->child, new->child);
		}
	}

}

/* locate and remove the file or directory. This function first sets isFirst to 1, it then
 * calls the function findPre. findPre will locate the previous element of the looked for node.
 * If the node is the child of the parent directory, isFirst will remain 1 otherwise it will be set to 0.
 * If the requested item is not in the directory, the findPre function will return NULL and rm() will
 * warn the user there is a problem. If the item exist and isFirst is 1, the function will set the current
 * directories child to the requested item's sibling. The requested item's sibling is then set to NULL
 * so we don't lose the sibling and cause a memory leak. The requested item is then deleted by the deletNode function,
 * If isFirst is 0, the previous elements sibling is set to the requested items sibling instead of the directories child.  */
void rm(char str[])
{
	isFirst = 1;
	node* preRemove = findPre(NULL,layer[counter]->child,str);
	if(preRemove != NULL)
	{
		if(isFirst == 1)
		{
			node* toRemove = layer[counter]->child;
			layer[counter]->child = toRemove->sib;
			toRemove->sib = NULL;
			deleteNode(toRemove);
		}
		else
		{
			node* toRemove = preRemove->sib;
			preRemove->sib = toRemove->sib;
			toRemove->sib = NULL;

			deleteNode(toRemove);
		}
	}
}

/* show path to first occurrence of file or directory if it exists. This function sets Counter2 to 0. This ensures that the
 * where is activity log is restarted. The function then calls find() to find the requested element. If the element is not found,
 * the function warns the user. If the element is found, The function uses a for loop the print ou the whereis activity log.
 * This shows the user where the item is located.*/
void whereIs(char str[])
{
	Counter2 = 0;
	node* found = find(layer[0], str);
	if(found != NULL)
	{
		printf("ROOT/");
		int y;
		for(y = 0; y < Counter2 - 1; y++)
		{
			printf("%s/", whereis[y]->name);;
		}
		printf("\n");
	}
	else
	{
		printf("%s not found in %s\n",str, layer[counter]->name);
	}
}

/* This function clones one node to another. This function calls itself recursively. Unlike Other recursive arrays in this program,
 * clone works will moving down the directory tree. Clone creates a new node and copies the values of the old node onto the new one.
 * It then pushes there children then there sibling to be cloned.*/

void clone(node* current, node* new)
{
	if(current->child != NULL)
	{
		node* newChild = malloc(sizeof(node));
		strcpy(newChild->name,current->child->name);
		newChild->isFolder = current->child->isFolder;
		newChild->sib = NULL;
		newChild->child = NULL;
		new->child = newChild;
		clone(current->child, new->child);
	}
	else if(current->sib != NULL)
	{
		node* newSib = malloc(sizeof(node));
		strcpy(newSib->name,current->sib->name);
		newSib->isFolder = current->sib->isFolder;
		newSib->sib = NULL;
		newSib->child = NULL;
		new->sib = newSib;
		clone(current->sib, new->sib);
	}
}

/*This function adds elements to the current directory. Whether it is a file or a folder, the function will add the new forder using the check function.
 * The check function sorts the current directory list in alphabetical order.*/
void add(node* item)
{
	node* test = find(layer[counter], item->name);
	if(test == NULL)
	{
		Counter2 = 0;
		if(layer[counter]->child != NULL)
		{
			check(layer[counter]->child, item);
		}
		else
		{

			layer[counter]->child = item;
		}
	}
	else
	{
		printf("%s already exists in %s", item->name, layer[counter]->name);
	}
}


/* Checks to find the appropriate location to add a new element. The function compares the elements in a director to find the alphebetical order.
 * The function use the elements ascii values to compare them. strcmp would return the total value of the string, what we need is the value of each
 * individual character. This is done by using Counter2. Since string are just char arrays we can go one by one comparing string[0] to string[0] and so on.
 * If the new element gets the the last element of the list, the function checks to make sure that the sibling of the last element is NULL.
 * It then adds the new element into that spot. If the new element is between to old elements. The element after the new is set as the new elements sib.
 * The new element is then set as the previous sibling. */
void check(node* folder, node* folder2)
{

	if((int)folder->name[Counter2] == (int)folder2->name[Counter2])
	{
		Counter2++;
		check(folder, folder2);
	}
	else if((int)folder->name[Counter2] > (int)folder2->name[Counter2])
	{
		folder2->sib = layer[counter]->child;
		layer[counter]->child = folder2;
	}
	else
	{
		if(folder->sib == NULL)
		{
			folder->sib = folder2;
		}
		else
		{
			if((int)folder->sib->name[Counter2] == (int)folder2->name[Counter2])
			{
				Counter2++;
				check(folder, folder2);
			}
			else if((int)folder->sib->name[Counter2] < (int)folder2->name[Counter2])
			{
				check(folder->sib, folder2);

			}
			else if((int)folder->sib->name[Counter2] > (int)folder2->name[Counter2])
			{
				folder2->sib = folder->sib;
				folder->sib = folder2;
				check(folder2, folder2->sib);
			}
		}

	}
}

/* Returns the node that is requested by the user. If the node is not located in the database, the funtion will return NULL.
 * The function takes in a node and a char array. By default, the user directory should be the first directory pushed though
 * The function then recursively calls itself first by puching the current items sibling, then pushing its child.
 * The function has if statements to prevent it from rerunning unnecessarly if the item is already found. */

node* find(node* current, char str[])
{
	if(current != NULL)
	{
		if (strcmp(current->name, str) == 0)
		{
			Counter2++;
			whereis[Counter2] = current;
			return current;
		}
		else
		{
			node* test = find(current->sib,str);
			if(test==NULL)
			{
				whereis[Counter2]=current;
				Counter2++;
				test = find(current->child,str);
			}
			if(test==NULL)
			{
				return NULL;
			}
			return test;
		}
	}
	else
	{
		return NULL;
	}
}

/* This function finds the previous element of the requested item. It is mostly just used in rm, but can be used in other
 * applications. This function takes in the previus node , which is NULL at first, then the current directories child,
 * and finally the string of the item it is looking for. It then uses an if statement to make sure the item is not NULL.
 * If the item passes the first test, it is then compared to the requested string. If it is a match, the previous node is returned.
 * If it is not a match, the function recursively calls on itself. This time the current element is pushed as the previus, and
 * current's sibling is pushed as current. isFirst is crucial since it serves as a safetynet to prevent the program from
 * assigning NULL->sib = requested->sib instead of director->child = requested->sib*/
node* findPre(node* previus, node* current, char str[])
{
	if(current != NULL)
	{
		if (strcmp(current->name, str) == 0)
		{
			return previus;
		}
		else
		{
			isFirst = 0;
			return findPre(current,current->sib,str);
		}
	}
	else
	{
		printf("%s not found\n", str);
		return NULL;
	}
}

/* Recursive function that deletes a node and all of its children/ grandchildren and so on. The function takes in the node to be deleted.
 * If the node is NULL. the fuction returns NULL. Otherwise, it calls deleteNode on its child, then on its sibling. Eventually, both the sibling and child
 * will return NULL which means, it is the last element of the list. The function then calls free() on the node to delete it.*/
void deleteNode(node* p)
{
	if(p != NULL)
	{
		deleteNode(p->child);
		deleteNode(p->sib);
		free(p);
	}
}

