CFLAGS = -g -Wall  
CC = gcc


objects = main.o 

main: $(objects)
	$(CC)  -o main $(objects)


main.o: main.c
.PHONY : clean
clean: 
	rm main $(objects)