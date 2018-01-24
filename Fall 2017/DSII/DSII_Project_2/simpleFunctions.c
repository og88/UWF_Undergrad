/*Simple recursive function for calculating powers*/
double pow(double z, double y)
{
	if(y < 1)
	{
		return (double)1;
	}
	else if(y == 1)
	{
		return z;
	}
	else
	{
		double x = pow(z, y-1);
		x = z * x;
		return x;
	}
}

 /*Factorial function that uses recursion to find factorial*/
float factorial(float x)
{
	if(x <= 1)
	{
		return 1;
	}
	else
	{
		float y = factorial(x-1);
		return (x + y);

	}
}

/*The percent idle time, Po; that is, the percentage of time that no one is in the system*/
double Po(double a, double u, double m)
{
	int i;
	float first = 0;
	float second = 0;
	for (i = 0; i < m; i ++)
	{
		first = first + ((1 / factorial((float)i))*pow((a/u), (float)i));

	}
	second = (1/factorial(m))*pow((a/u),m)*((m*u)/((m*u)-a));
	return 1/(first+second);
}

/*The average number of people in the system (the number in line plus the number being served*/
double L(double a, double u, double m)
{
	double top = ((a*u)*pow((a/u), m));
	double bottom = (factorial(m-1)*pow(((m*u)-a), (double)2));
	double full = ((top/bottom) * Po(m,u,a)) + (a/u);
	return full;
}

/*The average time a customer spends in the system.
 * (the time the customer spent in line plus the time spent getting service)*/
double W(double a, double u, double m)
{
	double full = L(2,3,2)/a;
	return full;
}

/*The average numbers of customers in the queue*/
double Lq(double a,double u,double m)
{
	return L(a,u,m) - (a/u);
}

/*The average time a customer spends waiting in the queue*/
double Wq(double a, double u, double m)
{
	return Lq(a,u,m)/a;
}
