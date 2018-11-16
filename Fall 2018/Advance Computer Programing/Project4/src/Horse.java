import java.util.Random;

/**
 * OMAR GARCIA
 * Project 3
 * Advanced Computer Programing
 */
public  class Horse implements Runnable {
    private static int distance;      //The distance to the finish line. Usually the window width minus finish line and horse length

    /**
     * Method used to determine how far the race will be. The horse will start at position 0 then run at random intervals
     * until it reaches the finish line.
     * @param meters distance to the finish line
     */
    public void setDistance(int meters) {

        distance = meters;   //The distance to finish line is set
    }
    /**
     * Method used for threading. This class implements runnable and the run method
     */
    public void run() {
        race();
    }

    /**
     * method used to simulate a horse running
     * @return status of horse running
     */
    public static int race()
    {
        int position = 0;                 //The horse starts at the starting line at position 0
        Random rand = new Random();       //Random  number used to calculate steps
        long startTime = System.currentTimeMillis();  //Record horses start time
        while(position < distance && !Thread.currentThread().isInterrupted())     //Loop used to keep the horse running until it reaches the finish line
        {
            try {
                Thread.sleep(500);          //Sleep the thread so the user can see the changes
            } catch (InterruptedException e) {
                System.out.println("Interrupted in horse sleep");  //If thread is interrupted, warn the user
                return -1;
            }

            position += (rand.nextInt(31) + 1);  //Add the random steps to current position to get new position
            System.out.println(position);
            GUI.update(Integer.parseInt(Thread.currentThread().getName()), position);    //Use GUI class to update this horses position

        }
        long total = System.currentTimeMillis() - startTime; //record the finish time minus start time to get total time
        horseRace.finish(total / 1000);  //send finish time to finish line
        return 0;
    }
}
