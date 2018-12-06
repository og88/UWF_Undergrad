import java.util.concurrent.locks.ReentrantLock;

public class Game {
    private static int[][] board = new int[3][3];
    private static int winner = 0;
    private static ReentrantLock lock = new ReentrantLock();
    private static int turn = 1;

    public static void lock()
    {
        lock.lock();
    }

    public static void unlock()
    {
        lock.unlock();
    }

    public static int getWinner() {
        return winner;
    }

    public static int getTurn() {
        return turn;
    }

    public static void switchTurns()
    {
        if(turn == 1)
        {
            turn = 2;
        }else
        {
            turn = 1;
        }
    }

    public static void game()
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0 ; j < 3; j++)
            {
                board[i][j] = 0;
            }
        }
    }

    public static void printTable()
    {
        for(int i = 0; i < 3; i++)
        {
        for(int j = 0 ; j < 3; j++)
        {
            System.out.print(board[i][j] + " ");
        }
        System.out.println("");
        }
    }

    public static int move(int id, int x, int y)
    {
        if(id > 0 && id < 3)
        {
            if(x >= 0 && x <3)
            {
                if(y >= 0 && y <3)
                {
                    if(board[x][y] == 0) {
                        board[x][y] = id;
                        int result = check();
                        if (result == 0) {
                            winner = id;
                        }else {
                            System.out.println("Turn over!");
                            switchTurns();
                        }
                        return result;
                    }
                    return 4;
                }
                return 3;
            }
            return 2;
        }
        return 1;
    }

    public static int check()
    {
        //rows
        for(int i = 0; i < 3; i++)
        {
            if(board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != 0)
            {
              return 0;
            }
        }
        //columns
        for(int i = 0; i < 3; i ++)
        {
            if (board[0][i] == board[1][i] && board[0][i] == board[2][i]  && board[0][i] != 0)
            {
                return 0;
            }
        }
        //across
        if(board[0][0] == board[1][1] && board[0][0] == board[2][2]  && board[0][0] != 0)
        {
            return 0;
        }
        if(board[0][2] == board[1][1] && board[0][2] == board[2][0]  && board[2][0] != 0)
        {
            return 0;
    }
        return -1;
    }
}
