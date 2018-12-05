class Customer
{
  String custName;
  int custAge;
  
  public Customer(String name, int age)
  {
    custName = name;
	 custAge = age;
  }
  
  public String toString()
  {
    return "name = " + custName + " Age = " + custAge;
  }
}