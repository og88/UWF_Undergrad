import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


/**
   This program demonstrates a set of strings. The user 
   can add and remove strings.
*/
public class SetDemoFactored
{

    Set<String> names = new HashSet<String>();  // Set interface
    Scanner in = new Scanner(System.in);

    public void demo()
	 {
	   DemoAdd();
		DemoRemove();
	 }

     public void DemoAdd()
	  {
     boolean done = false;
      while (!done)
      {
         System.out.print("Add name, Q when done: ");
         String input = in.next();
         if (input.equalsIgnoreCase("Q")) 
            done = true;
         else
         {
            names.add(input);
            print(names);
         }
      }
      }

public void DemoRemove()
{

       boolean done = false;
      while (!done)
      {
         System.out.print("Remove name, Q when done: ");
         String input = in.next();
         if (input.equalsIgnoreCase("Q")) 
            done = true;
         else
         {
            names.remove(input);
            print(names);
         }
      }
   }



 private void print(Set<String> s)
   {
      System.out.print("{ ");
      for (String element : s)
      {
         System.out.print(element);
         System.out.print(" ");
      }
      System.out.println("}");      
   }



   public static void main(String[] args)
   {

     SetDemoFactored sdf = new SetDemoFactored();
	  sdf.demo();
  }


}