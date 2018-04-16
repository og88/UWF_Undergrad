CFLAGS = -g -Wall  
CC = gcc


objects = bbserver.o

bbserver: $(objects)
	$(CC)  -o bbserver $(objects)


bbserver.o: bbserver.c Server.h

.PHONY : clean
clean: 
	rm bbserver $(objects)