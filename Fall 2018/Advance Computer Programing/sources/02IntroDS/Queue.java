public class Queue
{
   private QueueNode first;
	private QueueNode last;
	
	public Queue()
	{
	  first = null;
	  last = null;
	}
	
	
	public void enqueue(String n, int num)
	{
	  QueueNode tmp = new QueueNode(n, num);
	  if(first == null)
	  {
	    last = tmp;
		 first = last;
	  }
	  else
	  {
	   last.next = tmp;
		last = last.next;
	  }
	}
	
	public QueueNode dequeue()
	{
	  QueueNode tmp; 
	  if(isEmpty())
	    return null;
		 else
		 {
		   tmp = first;
			if(first == last)
			{
			  first = last = null;
			}
			else
			{
			  first = first.next;
			}
		 }
	  return tmp;
	}
	
	public boolean isEmpty()
	{
	  return first == null;
	}

  public static void main(String [] args)
  {
    Queue q = new Queue();
	 for(int i = 0; i < 3; i++)
	   q.enqueue("johnny" + i, 100 - i);
    while(!q.isEmpty())
      System.out.println(q.dequeue());
  }
}