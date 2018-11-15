import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class horseRace {
    private static ReentrantLock lock = new ReentrantLock();
    private static ArrayList<Thread> threads;

    public static int finish()
    {
        if(lock.tryLock())
        {
            try{
                Thread.currentThread().interrupt();

                for(Thread t : threads)
                {
                    if(Thread.currentThread().getId() != t.getId())
                    {
                        t.interrupt();
                    }
                }
            } finally{
                lock.unlock();
                return 0;
            }
        }
        else{
            return -1;
        }
    }


    public static void startThreads(int numHorses, ArrayList<Rectangle> horses, int width)
    {
        threads = new ArrayList<>();
        for(int i = 0; i < numHorses;i++) {
            Horse h = new Horse();
            h.setHorse(horses.get(i));
            h.setDistance(width);
            Thread t = new Thread(h, ("" + i));
            threads.add(t);
            t.start();
        }
    }
}
