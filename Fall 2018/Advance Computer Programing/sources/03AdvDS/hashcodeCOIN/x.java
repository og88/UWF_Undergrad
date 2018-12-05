import java.util.LinkedList;
import java.util.ListIterator;
class x
{
  public x()
  {
   LinkedList<String> people = new LinkedList<String>();
people.add("John");
people.add("Tom");
people.add("Sally");
people.add("Geoff");
ListIterator<String> iter = people.listIterator();
iter.next();
if(iter.next().equals("Sally"))
  iter.remove();
while(iter.hasNext())
  System.out.println(iter.next());

  }
  
  public static void main(String[]arge)
  {
    new x();
  }
}