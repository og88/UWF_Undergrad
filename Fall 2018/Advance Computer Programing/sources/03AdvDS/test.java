class test
{

public test()
{
  String st = "hello world how are you";
  
  for(int i = 0 ; i < st.length(); i++)
  {
    System.out.print(st.charAt(i));

  }
  	 System.out.println("");

   for(int i = 0 ; i < st.length(); i++)
  {
	 if(st.charAt(i) == ' ')
	   System.out.print("found a space");
  }

}

public static void main(String[] args)
{
  new test();
}

}