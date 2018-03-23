CFLAGS = -g -Wall  
CC = gcc


objects = HTTPServer.o

HTTPServer: $(objects)
	$(CC)  -o HTTPServer $(objects)


HTTPServer.o: HTTPServer.c Server.h

.PHONY : clean
clean: 
	rm HTTPServer $(objects)