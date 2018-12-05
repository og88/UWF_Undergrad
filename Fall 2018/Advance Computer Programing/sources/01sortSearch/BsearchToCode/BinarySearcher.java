
public class BinarySearcher
{  
   public BinarySearcher(Comparable[] anArray)
	{
	  a = anArray;
	} 
	
	
    public int search(Comparable v)
   { 
	  int low = 0;
	  int high = a.length - 1; // assume a is full!!! 
	  while(low <= high)
	  {
	    int mid = (low + high)/2;
		 if(a[mid].compareTo(v) ==0)
		   return mid;
		 else if(a[mid].compareTo(v) < 0)
		   low = mid + 1;
		 else 
		   high = mid - 1;
	  }
	  return -1;
	}
 private Comparable[] a; 
}

