public class FSA
{
    private String FSA = "5;x,y,z,a;(0:0:x),(0:1:y),(1:2:x),(2:2:x),(2:3:y),(3:3:x),(3:4:z),(4:4:x),(4:1:a);0;1,3;";
    private int numOfStates;
    private char[] alphabet;
    private int[][] transition;
    private int start;
    private int[] accepting;


    public int setStateNumber(int i)
    {
        String hold = "";
        for (i = i; i < FSA.length() && FSA.charAt(i) != ';';i++)
        {
            hold = hold + FSA.charAt(i);
        }
        numOfStates  = Integer.parseInt(hold);
        System.out.println("Number of state : " + numOfStates);
        return i + 1;
    }

    public int setAlphabet(int i)
    {
        if(i > 0)
        {
            int count = 0;
            String hold = "";

            for (i = i; i < FSA.length() && FSA.charAt(i) != ';'; i++) {
                if (FSA.charAt(i) == ',') {
                    count++;
                }
                hold = hold + FSA.charAt(i);
            }
            alphabet = new char[count + 1];
            int l = 0;
            for (int j = 0; j < hold.length(); j++) {
                if (hold.charAt(j) != ',') {
                    alphabet[l] = hold.charAt(j);
                    l++;
                }
            }

            for (int j = 0; j < alphabet.length; j++) {
                System.out.printf("%c ", alphabet[j]);
            }
            System.out.println("Alphabet : " + hold);
            return i + 1;
        }
        else
            return i;
    }

    public int setTransitions(int i)
    {
        if(i > 0)
        {
            transition = new int[numOfStates][alphabet.length];
            String hold = "";
            int count = 0;
            for (i = i; i < FSA.length() && FSA.charAt(i) != ';'; i++) {
                if (FSA.charAt(i) == ',') {
                    count++;
                }
                hold = hold + FSA.charAt(i);
            }
            System.out.println("Transitions : " + hold);
            int l = 0;
            for (int j = 0; j < count + 1; j++) {
                int start, end, location;
                if (hold.charAt(l) == '(') {
                    l++;
                    start = Character.getNumericValue(hold.charAt(l));
                    l++;
                    if (hold.charAt(l) == ':') {
                        l++;
                        end = Character.getNumericValue(hold.charAt(l));
                        l++;
                        if (hold.charAt(l) == ':') {
                            l++;
                            for (int k = 0; k < alphabet.length; k++)
                            {
                                if (hold.charAt(l) == alphabet[k])
                                {
                                    location = k;
                                    System.out.println("Transitioning from " + start + " to " + end + " when " + alphabet[k] + " is  found");
                                }
                            }
                            l++;
                        } else {
                            return -2;
                        }
                    } else {
                        return -2;
                    }
                } else {
                    return -1;
                }
                if (hold.charAt(l) != ')')
                {
                    return -3;
                }
                else
                {

                }
                l = l + 2;
            }
            return i + 1;
        }
        else
            return i;
    }

    public int setStart(int i)
    {
        if(i > 0)
        {
            String hold = "";
            for (i = i; i < FSA.length() && FSA.charAt(i) != ';'; i++) {
                hold = hold + FSA.charAt(i);
            }
            System.out.println("Start state : " + hold);
            return i + 1;
        }
        else
            return i;
    }

    public int setAccepting(int i)
    {
        if(i > 0)
        {
            String hold = "";
            int count = 0;
            for (i = i; i < FSA.length() && FSA.charAt(i) != ';'; i++) {
                if (FSA.charAt(i) == ',') {
                    count++;
                }
                hold = hold + FSA.charAt(i);
            }
            System.out.println("Accepting States : " + hold);
            return i + 1;
        }
        else
            return i;
    }
}
