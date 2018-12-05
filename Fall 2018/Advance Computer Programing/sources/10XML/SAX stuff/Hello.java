public class Hello
{
  public Hello()
  {
     double total = 10.5;
     for(int i = 0 ; i < total; i++)
	    System.out.println("" + i +" hello world!!");    
  }
 public Hello(int total)
  {
     for(int i = 0 ; i < total; i++)
	    System.out.println("" + i +" hello world!!");    
  }

  public static void main(String [] args)
  {
     new Hello();  
  }
}