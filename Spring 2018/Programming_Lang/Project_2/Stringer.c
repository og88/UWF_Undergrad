void add(int x, char* program);
void toString();

/*Concatinates the information needed to be displayed*/
void add(int x, char* program)
{
	char temp1[3];
	sprintf(temp1, "%c ", x);
	strcat(program, temp1);
}

/*Prints out a message to tell the user it is compiling. It displays the informtion of variables being stored into registers. It also prints out every vatraible us at the end*/
void toString(char* proName, char* program, char* hashTable[])
{
	int position;  //int used to keep track of position
	printf("Compiling %s\n", proName);
	printf("%s********[%s", program, hashTable[numofReservedWords + 1]);
	for (position = numofReservedWords + 2; position < arraySize + 1; position++)   //For loop to print out all identifiers used by the user
	{
		printf(", %s", hashTable[position]);
	}
	printf("]********\n");
}