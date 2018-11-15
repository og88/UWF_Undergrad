import java.util.concurrent.locks.ReentrantLock;

public class FinishLine {
    private static ReentrantLock lock = new ReentrantLock();

    public static boolean finish()
    {
            if(lock.tryLock())
            {
               return true;
            }
            else {
                return false;
            }
    }
}
