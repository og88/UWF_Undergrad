import java.util.concurrent.locks.ReentrantLock;

public class Game {
    private static int[][] board = new int[3][3];
    private static int winner = 0;
    private static ReentrantLock lock = new ReentrantLock();
    private static int turn = 1;
    public int player = 0;

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void lock()
    {
        lock.lock();
    }

    public void unlock()
    {
        lock.unlock();
    }

    public int getWinner() {
        return winner;
    }

    public int getTurn() {
        return turn;
    }

    public void switchTurns()
    {
        if(turn == 1)
        {
            turn = 2;
        }else
        {
            turn = 1;
        }
    }

    public Game()
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0 ; j < 3; j++)
            {
                board[i][j] = 0;
            }
        }
    }

    public String printTable()
    {
        String Table = "";
        for(int i = 0; i < 3; i++)
        {
        for(int j = 0 ; j < 3; j++)
        {
           Table += (board[j][i] + " ");
        }
        }
        return Table;
    }

    public int move(int id, int x, int y)
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
                            //System.out.println("Turn over!");
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

    public int check()
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
