CFLAGS = -g -Wall 
CC = gcc

objects = myshell.o 

myShell: $(objects)
	$(CC) -o myshell $(objects)

myshell.o: myshell.c parse.h
parse.o: parse.c parse.h
.PHONY : clean
clean: 
	rm myshell $(objects)
