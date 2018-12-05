import java.util.Arrays;
import java.util.Scanner;

/**
   This program demonstrates the binary search algorithm.
*/
public class BinarySearchDemo
{  
   public static void main(String[] args)
   {    
	   C[] theItems = new C[10];
		for(int i = 0; i < 10; i++)
		{
		  theItems[i] = new C("Johnny" + i, 100 + 2 * i);
		  System.out.println(theItems[i]);
		}
		
		

      BinarySearcher searcher = new BinarySearcher(theItems);
      Scanner in = new Scanner(System.in);

      boolean done = false;
      while (!done)
      {
         System.out.println("Enter number to search for, -1 to quit:");
         int n = in.nextInt();
         if (n == -1) 
            done = true;
         else
         {
           C tmp = new C("",n);
			  int pos = searcher.search(tmp);
            System.out.println("Found in position " + pos);
         }
      }
   }
}
