public class ALaCarte extends Vacation
{
	/**
	 * This class expands upon the vacation class. It inlcudes all the varaibles of vacation and adds
	 * A hotel name as a String, an airline name as a Strinng, the cost of a room in the hotel as a double
	 * the cost of an airline as a double, and the estimated cost of the meals as a double.
	 */
	String hotelName;
	double roomCost;
	String airline;
	double airfare;
	double meals;

	/**
	 * This is the default constructor. It sets all the values to a default value"", or 0
	 */
	ALaCarte()
	{
		this("",0,"",0,0);
	}

	/**
	 * This is the parameterized constructor
	 * @param name Sets the hotel name 
	 * @param room sets the room cost
	 * @param air sets the airline name
	 * @param fare sets the cost of the airfare
	 * @param meal sets the estimated cost of the meals
	 */
	ALaCarte(String name, double room, String air, double fare, double meal)
	{
		super();
		hotelName = name;
		roomCost = room;
		airline = air;
		airfare = fare;
		meals = meal;
	}

	/**
	 * This method returns the name of the hotel
	 */
	public String getHotelName()
	{
		return hotelName;
	}

	/**
	 * This method sets the name of a hotel to a user defined value
	 * @param name replaces the name of the hotel
	 */
	public void setHotelName(String name)
	{
		hotelName = name;
	}

	/**
	 * This method returns the cost of the room
	 */
	public double getRoomCost()
	{
		return roomCost;
	}

	/**
	 * This method changes the cost of the a room
	 * @param room sets the cost of a room to a user defined double
	 */
	public void setRoomCost(double room)
	{
		roomCost = room;
	}

	/**
	 * This method returns the airline name
	 */
	public String getAirline()
	{
		return airline;
	}

	/**
	 * This method sets the airline name to a user defined name
	 * @param air sets the airline name to a user define string
	 */
	public void setAirline(String air)
	{
		airline = air;
	}

	/**
	 * This method returns the value a the airfare
	 */
	public double getAirfare()
	{
		return airfare;
	}

	/**
	 * This method sets the value of an airfare to a user defined value
	 * @param fare sets the value of the fare to a user defined value
	 */
	public void setAirfare(double fare)
	{
		airfare = fare;
	}

	/**
	 * This method returns the value of meal, the estimated cost of meals in the vacation
	 */
	public double getMeal()
	{
		return meals;
	}

	/**
	 * This simple method sets the estimated meal cost to a user defined value
	 * @param meal replaces the cost of the meals
	 */
	public void setMeal(double meal)
	{
		meals = meal;
	}

	/**
	 * This method returns the  balance left over from the vacation. It tells the user
	 * whether they are under budget or over budget. It has been updated to add the meals,
	 * airfare, and room cost into the calculation.
	 */
	public double budgetBalance()
	{
		return budget - (meals + airfare + roomCost);
	}
}