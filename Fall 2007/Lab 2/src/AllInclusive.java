public class AllInclusive extends Vacation
{
	/**
	 * This class expands apound the vacation class
	 * It inlcudes the variables of vacation and adds
	 * brand name as a String, Rating as an int, and price as 
	 * a double.
	 */
	String brand;
	int rating;
	double price;
	
	/**
	 * default constuctor. sets brand to "", price to 0, and rating to 0
	 */
	AllInclusive()
	{
		this("",0,0);
	}

	/**
	 * This is the parameterizing constructor
	 * @param bran short for brand and sets the brand name to what the user wants
	 * @param rtg sets the rating to a user defined int value
	 * @param prce sets the price to a user defined double value
	 */
	public AllInclusive(String bran, int rtg, double prce) 
	{
		super();
		brand = bran;
		rating = rtg;
		price = prce;
		
	}
	
	/**
	 * This method returns the brand value
	 */
	public String getBrand()
	{
		return brand;
	}
	
	/**
	 * replace the current value of brand with a user defined value
	 * @param bran sets the brand name
	 */
	public void setBrand(String bran)
	{
		brand = bran;
	}
	
	/**
	 * This method return the rating value
	 */
	public int getRating()
	{
		return rating;
	}
	
	/**
	 * This method changes the rating to a user defined value
	 * @param rat sets the rating
	 */
	public void setRating(int rat)
	{
		rating = rat;
	}
	
	/**
	 * method to retrieve the value of price
	 */
	public double getPrice()
	{
		return price;
	}
	
	/**
	 * Sets the price value to a user defined value
	 * @param prce sets the price of the plan
	 */
	public void setPrice(double prce)
	{
		price = prce;
	}
	
	/**
	 * This method returns the  balance left over from the vacation. It tells the user
	 * whether they are under budget or over budget. This method has been updated to use
	 *  the price of the allInclusive plan
	 */
	public double budgetBalance()
	{
		return budget - price;
	}
}