/**
   A program that tests the LinkedList class
*/
public class ListTester
{  
   public static void main(String[] args)
   {  
      LinkedList<Customer> staff = new LinkedList<Customer>();
      staff.addFirst(new Customer("John1", 10));
      staff.addFirst(new Customer("John2", 20));
      staff.addFirst(new Customer("John3", 30));
      staff.addFirst(new Customer("John4", 40));

      // | in the comments indicates the iterator position

      ListIterator<Customer> iterator = staff.listIterator(); // |1234
      iterator.next(); // 4|321
      iterator.next(); // 43|21

      // Add more elements after second element
      
      iterator.add(new Customer("John5", 40)); // 435|21
      iterator.add(new Customer("John6", 40)); // 4356|21

      iterator.next(); // 43562|1

      // Remove last traversed element 

      iterator.remove(); // 4356|1
     
      // Print all elements

      iterator = staff.listIterator();
      while (iterator.hasNext())
      {
         Customer element = iterator.next();
         System.out.println(element + " ");         
      }
      System.out.println();
      System.out.println("Expected: 43561");
   }
}
