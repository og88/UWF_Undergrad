/**
   A program that tests the LinkedList class
*/
public class ListTester
{  
   public static void main(String[] args)
   {  
      LinkedList staff = new LinkedList();
      staff.addFirst("Tom");
      staff.addFirst("Romeo");
      staff.addFirst("Harry");
      staff.addFirst("Dick");
      
      // | in the comments indicates the iterator position

      ListIterator iterator = staff.listIterator(); // |DHRT
      iterator.next(); // D|HRT
      iterator.next(); // DH|RT

      // Add more elements after second element
      
      iterator.add("Juliet"); // DHJ|RT
      iterator.add("Nina"); // DHJN|RT

      iterator.remove(); // this will bomb the code because of
		// the handling of the previous reference in add()!!

      iterator.next(); // DHJNR|T

      // Remove last traversed element 

      iterator.remove(); // DHJN|T
     
      // Print all elements

      iterator = staff.listIterator();
      while (iterator.hasNext())
         System.out.print(iterator.next() + " ");
      System.out.println();
      System.out.println("Expected: Dick Harry Juliet Nina Tom");
   }
}
