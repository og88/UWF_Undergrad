import java.util.ArrayList;

public class GenericTest {

        ArrayList<Pair<String,Integer>> pairList1 = new ArrayList<Pair<String,Integer>>();

        public void addBatch()
        {
            for(int i = 0; i < 10; i++)
            {
                Pair<String,Integer> p1 = new Pair<String,Integer>("hello" +i, i + 50);
                pairList1.add(p1);
                pairList1.add(new Pair(1.5*i,"Goodbye" +i));
            }
        }
        public void showAll()
        {
            for(Pair<String,Integer> p: pairList1)
                System.out.println(p);
        }
}
