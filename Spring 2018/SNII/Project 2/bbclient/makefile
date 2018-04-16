CFLAGS = -g -Wall  
CC = gcc


objects = bbclient.o 

bbclient: $(objects)
	$(CC)  -o bbclient $(objects)


bbclient.o: bbclient.c Client.h
.PHONY : clean
clean: 
	rm bbclient $(objects)