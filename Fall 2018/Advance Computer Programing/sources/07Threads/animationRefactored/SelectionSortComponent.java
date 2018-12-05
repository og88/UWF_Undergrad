import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
   A component that displays the current state of the selection sort algorithm.
*/
public class SelectionSortComponent extends JComponent
{
   /**
      Constructs the component.
   */
   public SelectionSortComponent()
   {
      a = ArrayUtil.randomIntArray(30, 300);
      sorter = new SelectionSorter(a, this);
   }

   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D)g;
      draw(g2);
   }

/**
      Draws the current state of the sorting algorithm.
      @param g2 the graphics context
   */
   public void draw(Graphics2D g2)
   {
      sorter.sortStateLock.lock();
      try
      {
         int deltaX = this.getWidth() / a.length;
			int markedPosition = sorter.getMarkedPosition();
			int alreadySorted = sorter.getSortedPosition();
         for (int i = 0; i < a.length; i++)
         {
            if (i == markedPosition)
               g2.setColor(Color.RED);
            else if (i <= alreadySorted)
               g2.setColor(Color.BLUE);
            else
               g2.setColor(Color.BLACK);
            g2.draw(new Line2D.Double(i * deltaX, 0, 
                  i * deltaX, a[i]));
			   g2.draw(new Line2D.Double(i * deltaX + 1, 0, 
                  i * deltaX + 1, a[i]));
				g2.draw(new Line2D.Double(i * deltaX + 2, 0, 
                  i * deltaX + 2, a[i]));
				g2.draw(new Line2D.Double(i * deltaX + 3, 0, 
                  i * deltaX + 3, a[i]));
         }
      }
      finally
      {
         sorter.sortStateLock.unlock();
      }
   }



   /**
      Starts a new animation thread.
   */
   public void startAnimation()
   {
      class AnimationRunnable implements Runnable
      {
         public void run()
         {
            try
            {
               sorter.sort();
            }
            catch (InterruptedException exception)
            {
            }
         }
      }
      
      Runnable r = new AnimationRunnable();
      Thread t = new Thread(r);
      t.start();
   }
	
	

   private SelectionSorter sorter;
	int[] a;
}

