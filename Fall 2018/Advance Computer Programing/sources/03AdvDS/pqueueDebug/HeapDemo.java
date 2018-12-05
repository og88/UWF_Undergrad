/**
   This program demonstrates the use of a heap as a priority queue.
*/
public class HeapDemo
{
   public static void main(String[] args)
   {
      MinHeap q = new MinHeap();
		System.out.println("add shampoo");
      q.add(new WorkOrder(3, "Shampoo carpets"));	q.showHeap();
  System.out.println("add Empty tras");
      q.add(new WorkOrder(7, "Empty trash"));  	q.showHeap();
  System.out.println("add Water plants");
      q.add(new WorkOrder(8, "Water plants")); 	q.showHeap();
	System.out.println("add Pull Weeds");	
		q.add(new WorkOrder(8, "Pull Weeds")); 	q.showHeap();
  System.out.println("add Remove pencil");
      q.add(new WorkOrder(10, "Remove pencil sharpener shavings")); 	q.showHeap();
  System.out.println("add Replace light bulb");
      q.add(new WorkOrder(6, "Replace light bulb"));	q.showHeap();
System.out.println("add Buy mini");		
		q.add(new WorkOrder(6, "Buy mini "));	q.showHeap();
  System.out.println("add Fix broken sink");
      q.add(new WorkOrder(1, "Fix broken sink"));	q.showHeap();
  System.out.println("add Clean coffee maker");
      q.add(new WorkOrder(9, "Clean coffee maker"));	q.showHeap();
	System.out.println("add Water more plants");	
		q.add(new WorkOrder(8, "Water more plants"));	q.showHeap();
  System.out.println("add shampoo");
      q.add(new WorkOrder(2, "Order cleaning supplies"));	q.showHeap();


System.out.println("*************************removing now****************");

      while (q.size() > 0)
		{
			q.showHeap();
         System.out.println(" removing : " + q.remove());      
	   }
	}
}
