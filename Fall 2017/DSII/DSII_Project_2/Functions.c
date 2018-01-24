#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "simpleFunctions.c"

float GetNextRandomeInterval(float avg);

/*The structure Customer holds the information the program uses.
Id is the identification number for the customer. In this case, it is when the customer arrived.
The arrivalTime is when the customer arrive at the are/store.
Start of service is when the customer actually started getting served. It may differ from arrival time if the customer was in the FIFO queue
The departureTime is when the customer finished being served.
Nextcust is a pointer that points to the next customer in the FIFO queue
Arrival is a bollean that tracks whether the instance is an arrival or a departure.
CompareTime is the time to be compared when reorganizing the Priority Queue*/

struct Customer
{
	int id;
	float arrivalTime;         //holds the arrival time
	float StartOfService;      //holds the start of service time
	float departureTime;       //holds the departure time
	struct Customer *nextcust; //Links to the next customer in the FIFO queue
	int arrival;               //arrival 1, departure 0
	float compareTime;
};

/*Basic FIFO queue. The start element links all the other elements in a link list.
 * End is a pointer pointing to the last element. It is used to add more elements to the FIFO*/
struct FIFO
{
	int isEmpty;
	struct Customer start;
	struct Customer *end;
};

int n,     	  //number of Customers
batch = 1, 	  //Keep count of the number of batches we process
ncom = 0,     //Keep track of the total number of customers we served
event,		  //keep track of the position of the array
pqSize = 1;   //Size of priority Queue
double l, 	  //lamda
u,            //mu
M,            //server amount
m;            //available

struct Customer Pq[201]; //priority queue. location 0 is not used, so the queue wil start at 1 and end at 200
struct FIFO queue;       //FIFO queue stores customers waiting in line
float timer = 00.00,     //Global timer to keep track of time
		totalService,    //Total combined time in services
		totalWait;       //Total combined time waiting

/*Construct Batch creates a new set of customers. In the current configuration,
 * the priority queue will only handle 200 events at a time. So the function creates
 * Priority Queue size / 2 amount of customers. The customer start out ordered by arrival time.*/
void constructBatch()
{
int i;
	for(i = 1; ((i < 101) && (ncom <= n)); i++)
	{
		struct Customer cust;
		cust.arrivalTime = timer;  //sets arrival time to current timer
		cust.compareTime = timer;  //sets compare time to arrival time, since it is an arrival
		cust.arrival = 1;          //arrival set to 1 to indicate an arrival
		cust.id = ncom + 1;        //id set to its arrival. 1 is first
		cust.nextcust = malloc(sizeof(struct Customer));
		Pq[i] = cust;              //Arrival is placed in priority queue
		pqSize++;                  //Priority queue size is increased
		ncom++;                    //Number of customers is incremented
		timer = timer + GetNextRandomeInterval(l); //Timer for next arrival is set
	}
}

/*This function retreaves a random time interval. It first gets a float from 0..1.
 * The random variable is then log() and plugged into a formal to compute a random time variable.
 * This Time interval is used to calculate departures and arrivals*/
float GetNextRandomeInterval(float avg)
{
	float f = ((float)rand()/(float)RAND_MAX);  //random variable between 0..1
	float intervalTime = -1.0*(1.0/avg)*log(f); //negative exponential equation.
	return intervalTime;
}


/*This function adds an element to the priority queue. It checks every element in the queue and finds
 * the right spot for the one being added. The priority queue goes from lowest in time to highest.
 * It should not lose any elements*/
void addPq(struct Customer Custom)
{
	struct Customer Temp, Temp2;  //Temps so no data is lost
	Temp = Custom;                //Temp is first set to customer
	int z;                        //z is used for loop
	for(z = 1; z < pqSize + 1; z++)
	{
		if(z == pqSize)            //If the end of the loop is reached. The elementis the highest.
		{
			Pq[z] = Temp;          //Last spot is the temp
		}
		else
		{
			if(Temp.compareTime < Pq[z].compareTime)  //Switches the compared elements with a smaller one
			{
				Temp2 = Pq[z]; //bigger element is placed in Temp2
				Pq[z] = Temp;  //The element is replaced with the temp
				Temp = Temp2; //the bigger element is the new temp
			}
		}
	}
	pqSize++;                   //The priority queue size is increased
}

/*This function handles the next event in the priority queue. The priority queue is
 * dynamic,so it keeps ever expanding when more elements are added to it. This function
 * handles the addition of those elements. It takes in an arrival, and inserts a
 * departure. Since time only goes forward, the function will never choose a process that happened
 * before the one it is handling now.*/
void ProcessNextEvent()
{
	struct Customer cust; //Temporary element to hold current customer. Makes it easier to code
	cust = Pq[event];     //The temp is set to the customer we want to use
	if(cust.arrival == 1)
	{
		/*If statement make sure there is still enough servers available*/
		if(m > 0)
		{
			m=m-1; //available servers is decreased
			cust.StartOfService = cust.arrivalTime;  //Start of service is arrival time, since there is no FIFO queue wait
			cust.departureTime = cust.arrivalTime + (GetNextRandomeInterval(u)); //Departure time is calculated
			cust.compareTime = cust.departureTime; //Since this is now a departure, comparison is set to departure time
			totalService = cust.departureTime - cust.StartOfService; //Service time is calculated
			cust.arrival = 0; //arrival is set to zero to indicate departure
			addPq(cust);  //departure is added to priority queue

		}
		else
		{
			if(queue.isEmpty == 1)  //If FIFO is empty, start a new one
			{
				queue.start = cust;  //customer is set as the first person in line
				queue.end = &queue.start;
				queue.isEmpty--;
			}
			else
			{
				queue.end->nextcust = &cust; //customer is added to the end of the line
				queue.end = &cust; //Customer is now the end
				queue.isEmpty--;   //The queueu recieve another customer, so isEmpty is decremented
			}
		}
	}
	else
	{
		m++;
		if(queue.isEmpty != 1)
		{
			queue.start.StartOfService = cust.departureTime; //Start of service is set to the last departure
			queue.start.departureTime = queue.start.StartOfService + GetNextRandomeInterval(l); //Departure time is calculated
			queue.start.compareTime = queue.start.departureTime; //Since this is now a departure, comparison is set to departure time
			queue.start.arrival = 0;  //arrival is set to zero to indicate departure
			totalService = queue.start.departureTime - queue.start.StartOfService; //Service time is calculated
			addPq(queue.start);  //departure is added to priority queue
			queue.isEmpty++; //isempty is incremented when a customer leaves the FIFO
			if(queue.isEmpty != 1)
			{
				cust = queue.start;
				queue.start = *cust.nextcust; //Sets next person in line to the start
			}
			m--;

		}

	}
}



/*Print results traverses the Priority queue and prints out the elements properely.
 * It prints out arrival for an arrival and departure for a departure.*/
void printResults()
{
	printf("\nBatch %i\n", batch); //Batch number is printed
	int i;
	for(i = 1; i < pqSize; i++)
	{
		if(Pq[i].arrival == 1)
		{
			printf("Arrival %i: %f\n",Pq[i].id,Pq[i].arrivalTime); //prints arrival if its an arrival
		}
		else
		{
			printf("Departure %i: %f,  StartOfService: %f\n",Pq[i].id,Pq[i].departureTime,Pq[i].StartOfService);
		} //If it's a departure, the start of service is also printed
	}
}

/*This function prints out the stats needed at the end of the program.
 * This includes averages of idle, waiting, and service times*/
void printStats()
{
	printf("\n\nThe idle time was: %f\n"
				"The average amount of people in the system at one time: %f\n"
				"The average time a customer spent in the system: %f\n"
				"The average number of customers in the queue: %f\n"
				"The average time a customer spends waiting in the queue: %f\n",
				Po(l,u,M),
				L(l,u,M),
				W(l,u,M),
				Lq(l,u,M),
				Wq(l,u,M));
}


