import javafx.scene.control.Button;
import java.util.Random;

public  class Horse implements Runnable {
    Button btn;
    int distance;

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public void setMeters(int meters) {
        this.distance = meters;
    }

    public void Horse()
{

}


    public void run()
    {
        int meters = distance;
        Random rand = new Random();
        while(meters > 0)
        {
            meters -= rand.nextInt(distance / 10) + 1;

            btn.setTranslateX(meters);
            System.out.println("position = " + meters);
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
