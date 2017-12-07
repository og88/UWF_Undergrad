public class VacationTester
{
	public static void main(String[] args)
	{
	AllInclusive test = new AllInclusive("Sandals",4,4040.41);
	test.setDestination("Gahm");
	test.setBudget(5000.83);
	System.out.println(test.budgetBalance());
	
	AllInclusive test1 = new AllInclusive("Club med",4,8040.65);
	test1.setDestination("Japan");
	test1.setBudget(5000);
	System.out.println(test1.budgetBalance());
	
	ALaCarte test2 = new ALaCarte("Snowbird",2500.75,"United Airlines",1112,6530.89 );
	test2.setDestination("Hawaii");
	test2.setBudget(6300.5);
	System.out.println(test2.budgetBalance());
	
	ALaCarte test3 = new ALaCarte("La Fue",1500,"American Airlines",812.63,2530 );
	test3.setDestination("Munich");
	test3.setBudget(6300);
	System.out.println(test3.budgetBalance());
	
	Vacation vac[] = new Vacation[4];

	vac[0] = test;
	vac[1] = test1;
	vac[2] = test2;
	vac[3] = test3;
	
	System.out.println(test.getBrand());
	System.out.println(test1.getBudget());
	System.out.println(vac[0].getDestination());
	System.out.println(test1.getPrice());
	System.out.println(test2.getDestination());
	System.out.println(test3.getBudget());
	System.out.println(test2.getAirfare());
	System.out.println(test3.getAirline());
	System.out.println(test2.getHotelName());
	System.out.println(test3.getMeal());
	System.out.println(test2.getRoomCost());
	}
}