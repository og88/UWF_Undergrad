
public class Building implements CarbonFootprint{
	private String name = "";  //name of the building
	private double kWhyr = 0,  //The average carbon intensity used by the electricity
	EF = 0.7; //Average for the US
	
	/*Accessors and mutators*/
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	
	public double getkWhyr()
	{
		return kWhyr;
	}
	public void setkWhyr(double kWhyr) 
	{
		this.kWhyr = kWhyr;
	}
	public double getEF()
	{
		return EF;
	}
	public void setEF(double eF) 
	{
		EF = eF;
	}
	
	/*Method to calculate Emmission Factors*/
	@Override
	public double getCarbonFootprint() 
	{
		double carbonFootprint = (kWhyr * EF);  //Calculates the emission caused by a building
		System.out.printf("The %s's carboon footprint is %.2f lbs per year\n", name, carbonFootprint);
		return carbonFootprint;
	}

}
