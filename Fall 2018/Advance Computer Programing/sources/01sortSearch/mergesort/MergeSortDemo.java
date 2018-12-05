import java.util.Arrays;

/**
   This program demonstrates the merge sort algorithm by
   sorting an array that is filled with random numbers.
*/
public class MergeSortDemo
{  
   public static void main(String[] args)
   {  
     // int[] a = ArrayUtil.randomIntArray(13, 100);
	  int[] a = {88,7,66,5,4,67,77,34,29,46,119,43,23};
      System.out.println(Arrays.toString(a));

      MergeSorter sorter = new MergeSorter(a);
      sorter.sort();
      System.out.println(Arrays.toString(a));
   }
}

