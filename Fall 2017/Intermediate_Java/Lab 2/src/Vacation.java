public class Vacation
{
	/**
	 * The class holds the destination in a String and the budget as a double
	 */
	String destination;
	double budget;
	

	
	/**
	 * Default constructor. Sets all variable to a default value.
	 * destination name is set to "", while budget is set to 0.
	 */
	Vacation()
	{
		this("",0);
	}
	
	/**
	 * Parameterized constructor. Sets all the values of vacation to a user defined
	 * values.
	 * @param dest sets the value of destination
	 * @param budg sets the value of budget
	 */
	public Vacation(String dest, double budg)
	{
		destination = dest;
		budget = budg;
	}
	
	
	
	/**
	 * Simple method to retrieve the destination name
	 * @return
	 */
	public String getDestination()
	{
		return destination;
	}
	
	/**
	 * Simple method to replace the value of destination to a user defined value.
	 * @param dest
	 */
	public void setDestination(String dest)
	{
		destination = dest;
	}
	
	/**
	 * Simple method to return the budget amount
	 * @return
	 */
	public double getBudget()
	{
		return budget;
	}
	
	/**
	 * Sets the budget to a user define value.
	 * @param budg is used to replace the budget amount.
	 */
	public void setBudget(double budg)
	{
		budget = budg;
	}
	
	/**
	 * This method returns the  balance left over from the vacation. It tells the user
	 * whether they are under budget or over budget.
	 * @return
	 */
	public double budgetBalance()
	{
		return Integer.MIN_VALUE;
	}
}