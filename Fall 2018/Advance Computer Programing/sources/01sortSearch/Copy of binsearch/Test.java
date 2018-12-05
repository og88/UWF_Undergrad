public class Test implements Comparable
{
  String name;
  int age;
  
  public int compareTo(Comparable other)
  {
    return name.compareTo(other);
  }
}