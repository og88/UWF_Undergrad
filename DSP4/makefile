CFLAGS = -g -Wall  
CC = gcc


objects = Start.o 

Start: $(objects)
	$(CC)  -o Start $(objects) -lm


Start.o: Start.c
.PHONY : clean
clean: 
	rm Start $(objects)