import java.util.LinkedList;
import java.util.ListIterator;

/**
   A program that tests the LinkedList class
*/
public class ListTester
{  
   public static void main(String[] args)
   {  
      LinkedList<String> staff = new LinkedList<String>();
      staff.addLast("Dick");
      staff.addLast("Harry");
      staff.addLast("Romeo");
      staff.addLast("Tom");
     System.out.println("after 4 adds");    
      // | in the comments indicates the iterator position

      ListIterator<String> iterator 
            = staff.listIterator(); // |DHRT
      //iterator.remove(); // uncomment for runtime error
      
      iterator.next(); // D|HRT
      iterator.next(); // DH|RT
     System.out.println("after 2 nexts");
      // Add more elements after second element
      
      iterator.add("Juliet"); // DHJ|RT    J is previous, R is next!
      iterator.add("Nina"); // DHJN|RT
      System.out.println("after 2 more adds");
      iterator.next(); // DHJNR|T

      // Remove last traversed element 

      iterator.remove(); // DHJN|T  removes previous, not current
      System.out.println("after remove");
      // Print all elements
      //iterator = staff.listIterator(); // comment this and it will not start at the beginning
      for(String name :staff)  
	   //while(iterator.hasNext())
         System.out.print(iterator.next() + " ");
      System.out.println();
      System.out.println("Expected: Dick Harry Juliet Nina Tom");
   }
}
