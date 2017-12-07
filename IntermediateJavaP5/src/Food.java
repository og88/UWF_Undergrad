
public class Food implements CarbonFootprint{
	private String category = " ";
	private double consumption = 0,
			EF = 0; //emission factor

	/*Accessprs and Mutators*/
	public String getName() 
	{
		return category;
	}

	public void setName(String category) 
	{
		this.category = category;
	}

	public double getConsumption() 
	{
		return consumption;
	}

	public void setConsumption(double consumption) 
	{
		this.consumption = consumption;
	}

	public double getEF() 
	{
		return EF;
	}

	/*Unique method to set emission factor*/
	public void setEF(String x) 
	{
		switch(x) //Switch statement to determine what kind of food the object is
		{         //When identified, the appropriate EF is assigned
		case "b":
		case "B":
			EF = 0.77;
			break;
		case "p":
		case "P":
			EF = 0.34;
			break;
		case "d":
		case "D": 
			EF = 0.46;
			break;
		case "c":
		case "C":
			EF = 0.30;
			break;
		case "v":
		case "V":
			EF = 0.12;
			break;
		case "f":
		case "F":
			EF = 0.15;
			break;
		case "o":
		case "O":
			EF = 0.18;
			break;
		case "s":
		case "S": 
			EF = 0.06;
			break;
		case "r":
		case "R":
			EF = 0.14;
			break;
		}
	}


	
	/*Method to calculate Emmission Factors*/
	@Override
	public double getCarbonFootprint() 
	{
		double carbonFootprint = consumption * 365 * EF;  //Calculates the emission cased by the food
		System.out.printf("A(n) %s's carboon footprint is %.2f lbs per year\n", category, carbonFootprint);
		return carbonFootprint;
	}

}
