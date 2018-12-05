/**
   This class sorts an array, using the selection sort 
   algorithm
*/
public class RippleSorter
{
   /**
      Constructs a selection sorter.
      @param anArray the array to sort
   */
   public RippleSorter(int[] anArray)
   {
      a = anArray;
   }

   /**
      Sorts the array managed by this selection sorter.
   */
   public void sort()
   {  
      for (int i = 0; i < a.length - 1; i++)
      {  
         for(int j = i+1; j < a.length; j++)
			  if(a[i] > a[j])
             swap(i,j);
      }
   }

  
   /**
      Swaps two entries of the array.
      @param i the first position to swap
      @param j the second position to swap
   */
   private void swap(int i, int j)
   {
      int temp = a[i];
      a[i] = a[j];
      a[j] = temp;
   }

   private int[] a;
}
