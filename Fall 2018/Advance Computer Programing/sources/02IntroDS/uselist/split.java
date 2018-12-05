
public class split
{ 
  public void demosplit()
  {
     String st = "-2735628756283765 + 2975348965349865";
	  String[] expr = st.split(" ");
	  
	  System.out.println(expr[0]); 
	  System.out.println(expr[1]); 
	  System.out.println(expr[2]);  
  }

 
   public static void main(String[] args)
   {  
     split s = new split();
	  s.demosplit();
	}
}
