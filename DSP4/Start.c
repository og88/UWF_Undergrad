/*
 * Start.c
 *
 *  Created on: Nov 16, 2017
 *      Author: omarg
 */
#include "MonteCarlo.c"
#include "MonteCarloAlgorithm.c"
int main(int argc, const char* arg[] )
{
	launchMonteCarlo(); //Monte Carlo simulation
	launchMonteCarloAlgorithm(); //MonteCarlo Algorithm simulation
	return 0;
}
