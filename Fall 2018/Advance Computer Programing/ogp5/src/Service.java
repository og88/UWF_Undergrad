public class Service {
    private static Game ticktock;
    private static int ID;
    public  void main(String[] args){
        ticktock = new Game();

        String moves;

        try {
            ID = 1;
            ticktock.lock();
            if(ID == ticktock.getTurn()) {
                ticktock.printTable();
                moves = "100";
                move(moves);
                ticktock.printTable();
            }else
            {
                System.out.println("Other users turn");
            }
        } finally{
            ticktock.unlock();
        }

        try {
            ID = 1;
            ticktock.lock();
            if(ID == ticktock.getTurn()) {
                ticktock.printTable();
                moves = "110";
                move(moves);
                ticktock.printTable();
            }else
            {
                System.out.println("Other users turn");
            }
        } finally{
            ticktock.unlock();
        }

        try {
            ID = 2;
            ticktock.lock();
            if(ID == ticktock.getTurn()) {
                ticktock.printTable();
                moves = "220";
                move(moves);
                ticktock.printTable();
            }else
            {
                System.out.println("Other users turn");
            }
        } finally{
            ticktock.unlock();
        }

        try {
            ID = 1;
            ticktock.lock();
            if(ID == ticktock.getTurn()) {
                ticktock.printTable();
                moves = "120";
                move(moves);
                ticktock.printTable();
            }else
            {
                System.out.println("Other users turn");
            }
        } finally{
            ticktock.unlock();
        }
    }

    public static void move(String moves)
    {
        int id = moves.charAt(0) - '0';
        int x = (int)moves.charAt(1)- '0';
        int y = (int)moves.charAt(2)- '0';

       // System.out.println(id);
       // System.out.println(x);
       // System.out.println(y);

        messageHandler(ticktock.move(id,x,y));
    }

    public static void messageHandler(int e)
    {
       if(e == 0)
       {
          System.out.println("Winner is " + ticktock.getWinner());
       } else if(e == 1)
       {
           System.out.println("Invalid id");
       } else if(e == 2)
       {
           System.out.println("x value out of range");
       } else if(e == 3)
       {
           System.out.println("y value out of range");
       } else if(e == 4)
       {
           System.out.println("space is already taken");
       }
    }
}
