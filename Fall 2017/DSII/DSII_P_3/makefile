CFLAGS = -g -Wall -lm
CC = gcc

objects = TravelingSalesMan.o

TravelingSalesMan: $(objects)
	$(CC) -o TravelingSalesMan  $(objects) $(CFLAGS)


TravelingSalesMan.o: TravelingSalesMan.c Genetic.c 
Genetic.o: Genetic.c BruteForce.c
BruteForce.o: BruteForce.c TravelFunc.c
TravelFunc.o: TravelFunc.c
.PHONY : clean
clean: 
	rm TravelingSalesMan $(objects)
