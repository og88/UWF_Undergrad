CFLAGS = -g -Wall  
CC = gcc


objects = LCS.o 

LCS: $(objects)
	$(CC)  -o LCS $(objects)


LCS.o: LCS.c
.PHONY : clean
clean: 
	rm LCS $(objects)