import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JComponent;

/**
   This class sorts an array, using the selection sort 
   algorithm.
*/
public class SelectionSorter
{
   /**
      Constructs a selection sorter.
      @param anArray the array to sort
      @param aComponent the component to be repainted when the animation 
      pauses
   */
   public SelectionSorter(int[] anArray, SelectionSortComponent aComponent)
   {
      a = anArray;
      sortStateLock = new ReentrantLock();
      component = aComponent;
   }

   public int getMarkedPosition()
	{
	  return markedPosition;
	}

   public int getSortedPosition()
   {
	  return alreadySorted;
	}
	
   /**
      Sorts the array managed by this selection sorter.
   */
   public void sort() 
         throws InterruptedException
   {  
      for (int i = 0; i < a.length - 1; i++)
      {  
         int minPos = minimumPosition(i);
         sortStateLock.lock();
         try
         {
            swap(minPos, i);
            // For animation
            alreadySorted = i;
         }
         finally
         {
            sortStateLock.unlock();
         }
         pause(2);
      }
   }

   /**
      Finds the smallest element in a tail range of the array
      @param from the first position in a to compare
      @return the position of the smallest element in the
      range a[from]...a[a.length - 1]
   */
   private int minimumPosition(int from)
         throws InterruptedException
   {  
      int minPos = from;
      for (int i = from + 1; i < a.length; i++)
      {
         sortStateLock.lock();
         try
         {
            if (a[i] < a[minPos]) minPos = i;
            // For animation
            markedPosition = i;
         }
         finally
         {
            sortStateLock.unlock();
         }
         pause(1);
      }
      return minPos;
   }

   /**
      Swaps two entries of the array.
      @param i the first position to swap
      @param j the second position to swap
   */
   private void swap(int i, int j)
   {
      int temp = a[i];
      a[i] = a[j];
      a[j] = temp;
   }

   
   /**
      Pauses the animation.
      @param steps the number of steps to pause
   */
   public void pause(int steps) 
         throws InterruptedException
   {
      component.repaint();
      Thread.sleep(steps * DELAY);
   }

   private int[] a;

   // The component is repainted when the animation is paused
   private SelectionSortComponent component;   
 
 	private int markedPosition = -1;
   private int alreadySorted = -1;
   
   Lock sortStateLock;
	
   private static final int DELAY = 20;
}
