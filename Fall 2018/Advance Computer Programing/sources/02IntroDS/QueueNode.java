public class QueueNode
{
  private String name;
  private int number;
  QueueNode next;
  
  public QueueNode(String na, int num)
  {
    name = na;
	 number = num;
  }
  
  public String toString()
  {
    return "name = " + name + " number = " + number;
  }
}