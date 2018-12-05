/**
   A program that tests the LinkedList class
*/
public class ListTestCust
{  
   public static void main(String[] args)
   {  
      LinkedList<Customer> staff = new LinkedList<Customer>();
		for(int i = 0; i < 20; i++)
        staff.addFirst(new Customer("name" + i, i + 50));
      ListIterator<Customer> iterator = staff.listIterator();

      while (iterator.hasNext())
      {
         Customer element = iterator.next();
         System.out.println(element + " ");         
      }
      System.out.println();
   }
}
