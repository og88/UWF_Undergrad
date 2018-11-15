import javafx.scene.shape.Rectangle;
import java.util.Random;

public  class Horse implements Runnable {
    private Rectangle horse;   //Object used to display the horse in the race.
    private static int distance;      //The distance to the finish line. Usually the window width - 10%
    private double offset = .8;  //The track will start at the left side and span 90% of the window

    /**
     * This method assigns a javafx object to the horse. This object will be the visual representation of the horse.
     * @param horse   Rectangle used to represent the horse visually
     */
    public void setHorse(Rectangle horse) {
        this.horse = horse;
    }

    /**
     * Method used to determine how far the race will be. The horse will start at position 0 then run at random intervals
     * until it reaches the finish line. An offeset
     * @param meters
     */
    public void setDistance(int meters) {

        distance = meters - (int)horse.getWidth();   //The finish line will be about 10% away from the right side of the window
    }                                                //This offset will make it so the horse won't run off screen
                                                     // subtracting the horses width makes it so the race ends when the horse reaches the finish line.

    /**
     * Method used to simulate the horse running.
     */
    public void run() {
        //System.out.println("running");
        race();
    }

    public static int race()
    {
        int position = 0;                 //The horse starts at the starting line at position 0
        Random rand = new Random();       //Random  number used to calculate steps
        while(position < distance && !Thread.currentThread().isInterrupted())     //Loop used to keep the horse running until it reaches the finish line
        {
            position += (rand.nextInt(31) + 1);  //Add the random steps to current position to get new position

            System.out.println("position = " + position);

            GUI.update(Integer.parseInt(Thread.currentThread().getName()), position);    //Use GUI class to update this horses position

            try {
                Thread.sleep(500);          //Sleep the thread so the user can see the changes
            } catch (InterruptedException e) {
                //return 1;
                System.out.println("Interrupted in horse sleep");//e.printStackTrace();              //If thread is interrupted, warn the user
                return -1;
            }

        }
        if(horseRace.finish() == 0) {
            Thread.currentThread().interrupt();
            System.out.println("Horse " + Thread.currentThread().getName() + " Won!");
            return 0;
        }
        return 0;
    }
}
