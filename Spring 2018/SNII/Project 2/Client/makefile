CFLAGS = -g -Wall  
CC = gcc


objects = HTTPClient.o 

HTTPClient: $(objects)
	$(CC)  -o HTTPClient $(objects)


HTTPClient.o: HTTPClient.c Client.h
.PHONY : clean
clean: 
	rm HTTPClient $(objects)