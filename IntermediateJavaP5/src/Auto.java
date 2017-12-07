
public class Auto implements CarbonFootprint{
	private String name = "";  //name of the car. (year make model) or (year, make, model)
	private double MPG = 0,   //Efficiency
			DE = 0,  //Direct Emission
			IDE = 0, //Indirect Emission
			CE = 0,  //Construction Emission
			TE = 0;  //Total Emission
	
	/*assesors and mutators*/
	public String getName() 
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public double getMPG() 
	{
		return MPG;
	}



	public void setMPG(double mPG)
	{
		MPG = mPG;
	}



	public double getDE()
	{
		return DE;
	}



	public void calculateDE() //Calculates Direct Emission using (8.78 kgCO2e/US_gallon)/MPG
	{
		DE = 8780/MPG;
	}



	public double getIDE()
	{
		return IDE;
	}



	public void calculateIDE() //Calculates Indirect Emission using (1.55 kgCO2e/US_gallon)/MPG
	{
		IDE = 1550 / MPG;
	}



	public double getCE() 
	{
		return CE;
	}



	public void calculateCE(double c, double w) //Calculates construction emission by using tonnes(c)/lifetime mileage(w)
	{
		CE = c/w;
	}



	public double getTE() 
	{
		return TE;
	}



	public void setTE(double tE) 
	{
		TE = tE;
	}

	/*Method to calculate Emmission Factors*/
	@Override
	public double getCarbonFootprint() 
	{
		TE = DE + IDE + CE;
		double carbonFootprint =  MPG*TE; //Calculates the emission caused by a Vehicle
		System.out.printf("My %s's carboon footprint is %.2f lbs per year\n", name, carbonFootprint);
		return carbonFootprint;
	}

}
