public class C implements Comparable
{
  String name;
  int number; // assume this is an identifier

  public C(String na, int num)
  {
    name = na;
	 number = num;
  }
  /* compareTo returns +, 0, or - */
  public int compareTo(Object other)
  {
    // explicit typecast to type C
    C compTo = (C)other;
	 return number - compTo.number;
  }
  public String toString()
  {
    return "name = " + name + " number = " + number;
  }
 }