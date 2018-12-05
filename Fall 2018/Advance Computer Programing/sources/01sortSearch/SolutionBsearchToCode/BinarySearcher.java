      /**
   A class for executing binary searches through an array.
*/
public class BinarySearcher
{  
   /**
      Constructs a BinarySearcher.
      @param anArray a sorted array of integers
   */
   public BinarySearcher(Comparable[] anArray)
   {
      a = anArray;
   }
   
   /**
      Finds a value in a sorted array, using the binary
      search algorithm.
      @param v the value to search
      @return the index at which the value occurs, or -1
      if it does not occur in the array  */

   public int search(Comparable v)
   {  
	  int low = 0;
	  int high = a.length-1;
	  while(low <= high)
	  {
	    int mid = (low + high)/2;
	    if (a[mid].compareTo(v)== 0)
		    return mid;
		 else if(a[mid].compareTo(v)< 0)
		   low = mid + 1;
		 else
		   high = mid -1;
	  }
	  return -1;
	}
   


   public int search(Comparable v, int low, int high)
   {
	  if(low <= high)
	  {
	   int mid = (low + high) /2;
		 if (v.compareTo(a[mid]) == 0)
	      return mid;
	    else if(a[mid].compareTo(v)< 0)
		   return search(v,mid + 1,high);
		 else
		   return search(v,low,mid - 1); 
	  }
	  else  
       return -1;  
	}

   private Comparable[] a;
}

