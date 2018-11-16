import javafx.scene.control.Label;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * OMAR GARCIA
 * Project 3
 * Advanced Computer Programing
 */
public class horseRace {
    private static ReentrantLock lock = new ReentrantLock(); //Lock used to prevent losing horses from finishing the race
    private static ArrayList<Thread> threads;  //Array of threads, this willbe used to tell the threads to stop once a winner is declared
    private static String winner = null;  //name of the winning horse
    private static Boolean finished = false;  //Used to determine whether the race has finished
    private static Boolean started = false;  //Used to determine if a rac is underway

    /**
     * simple methos used to set whether a race has finished
     * @param finish
     */
    public static void setFin(Boolean finish)
    {
        finished = finish;
    }

    /**
     * Simple method used to find out whether a race has finished
     * @return returns status of race
     */
    public static Boolean isFinished()
    {
        return finished;
    }

    /**
     * Simple method used to see if a race has already started
     * @return returns status of race
     */
    public static Boolean getStarted() {
        return started;
    }

    /**
     * Simplle method used to update the start status
     * @param started status of the start state
     */
    public static void setStarted(Boolean started) {
        horseRace.started = started;
    }

    /**
     * Method used to retrieve winning horse
     * @return returns the name of the winning horse
     */
    public static String getWinner()
    {
        return winner;
    }

    /**
     * Method used when a horse crosses the finish line. 
     * The horse will take the lock, then prevent the other horses from finishing.
     * The horse interupts the other horses, which causes them to stop racing
     * @param time  time it took the horse to get to the finish line
     * @return returns status of the finish 0 if placed, -1 if lost
     */
    public static int finish(Long time)
    {
        if(lock.tryLock())  //try to aquire the lock
        {
            winner = Thread.currentThread().getName();  //if lock is acquired, horse declares intself the winner
            finished = true;  //race has finished
            started = false;  //race is over, so it is not started
            Platform.runLater(() -> {
                GUI.showWinner(winner, time);  //Since this is not a Javax thread, the program schedules a pop up to be run later in the program
            });
            try{
                for(Thread t : threads)  //Send an inturrupt to each thread telling it to stop racing
                {
                    if(Thread.currentThread().getId() != t.getId())  //Don't inturrupt current thread
                    {
                        t.interrupt();
                    }
                }
            } finally{
                lock.unlock();  //unlock lock
            }
            return 0;  //method finished
        }
        else{
            return -1;  //error occured
        }
    }


    /**
     * Method used to start all threads
     * @param numHorses The amount of horses racing
     * @param horses A list of horse objects to be manipulated
     * @param width  Length of race track
     */
    public static void startThreads(int numHorses, ArrayList<Label> horses, int width)
    {
        threads = new ArrayList<>();  //create an array of threads
        for(int i = 0; i < numHorses;i++) { //create a horse object that will race and manipulate the horse visual object
            Horse h = new Horse(); 
            //h.setHorse(horses.get(i));  //give each horse an horse visual object to manipulate
            h.setDistance((width - (int)horses.get(i).getWidth()) - 10);  //set the distance the horse will travel
            Thread t = new Thread(h, ("" + i));  //create thread with its id being the number thread it is
            threads.add(t);  //add to thread list
            t.start();  //start thread
        }
    }
}
