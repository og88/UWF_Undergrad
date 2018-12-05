class C implements Comparable
{
  String name;
  int number;

  public C(String na, int num)
  {
    name = na;
	 number = num;
  }
  
  public int compareTo(Object other)
  {
     C compTo = (C)other;
	   return number - compTo.number;
  }
  
  public String toString()
  {
    return "name = " + name + " number = " + number;
  }
}