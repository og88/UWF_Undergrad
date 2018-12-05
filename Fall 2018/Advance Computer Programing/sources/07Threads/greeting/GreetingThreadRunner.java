/**
   This program runs two greeting threads in parallel.
*/
public class GreetingThreadRunner
{
   public static void main(String[] args)
   {
      Runnable r1 = new GreetingRunnable("Hello, World!");
      Runnable r2 = new GreetingRunnable("Goodbye, World!");
      Thread t1 = new Thread(r1);
      Thread t2 = new Thread(r2);
      t1.start();
      t2.start();
   }
}

