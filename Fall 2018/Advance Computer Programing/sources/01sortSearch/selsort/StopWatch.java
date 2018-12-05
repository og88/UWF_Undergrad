/**
   A stopwatch accumulates time when it is running. You can 
   repeatedly start and stop the stopwatch. You can use a
   stopwatch to measure the running time of a program.
*/
public class StopWatch
{  
   /**
      Constructs a stopwatch that is in the stopped state
      and has no time accumulated.
   */
   public StopWatch()
   {  
      reset();
   }

   /**
      Starts the stopwatch. Time starts accumulating now.
   */
   public void start()
   {  
      if (isRunning) return;
      isRunning = true;
      startTime = System.currentTimeMillis();
   }
   
   /**
      Stops the stopwatch. Time stops accumulating and is
      is added to the elapsed time.
   */
   public void stop()
   {  
      if (!isRunning)
		     return;
      isRunning = false;
      elapsedTime = System.currentTimeMillis() - startTime;
   }
   
   /**
      Returns the elapsed time so far in a timed event 
		(if we have not stopped timing yet)
		 or the elapsed time of the last timed event after 
		 stopping the timer.
   */    
	
	   public long getElapsedTime()
   {  
      if (isRunning) 
      {  
         return System.currentTimeMillis() - startTime;
      }
      else
         return elapsedTime;
   } 
	
	
   /**
      Stops the watch and resets the elapsed time to 0.
   */
   public void reset()
   {  
      elapsedTime = 0;
      isRunning = false;
   }
   
   private long elapsedTime;
   private long startTime;
   private boolean isRunning;
}
