public class Main {

    public static void main(String[] args) {
        //startThreads();
        GUI.main(null);

    }

    public static void startThreads()
    {
        for(int i = 0; i < 4;i++)
        {
          // Thread t =  new Thread(new Horse(),("" + i));
            //t.start();
        }
        GUI.main(null);
    }

}
