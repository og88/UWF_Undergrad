import javafx.scene.control.Label;

import java.util.Random;

public  class Horse implements Runnable {
    Label horse;
    int distance;

    public void setHorse(Label horse) {
        this.horse = horse;
    }

    public void setMeters(int meters) {
        this.distance = (meters-(int)(meters*.1));
    }


    public void run()
    {
        int position = 0;
        Random rand = new Random();
        while(position < distance)
        {
            position += (rand.nextInt(11) + 1);

            System.out.println("position = " + position);

            GUI.update(Integer.parseInt(Thread.currentThread().getName()), position);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean first = FinishLine.finish();
        if(first)
        {
            System.out.println("Horse " + Thread.currentThread().getName() + " Won!");
        }
        else{
            System.out.println("Horse " + Thread.currentThread().getName() + " Lost!");
        }
    }
}
