CFLAGS = -g -Wall -O0 
CC = gcc


objects = matrix.o 

Start: $(objects)
	$(CC)  -o matrix $(objects)


matrix.o: matrix.c
.PHONY : clean
clean: 
	rm matrix $(objects)