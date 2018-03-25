public class FSA {
    private String FSA;
    private int numOfStates;
    private static char[] alphabet;
    private int[][] transition;
    private int start;
    private int[] accepting;
    private boolean set = false;

    /*Sets the private String FSA to a user defined FSA*/
    public void setFSA(String s)
    {
        FSA = s;
    }

    public boolean getStatus()
    {
        return set;
    }

    public void setStatus(boolean x)
    {
        this.set = x;
    }

    public int getNumState()
    {
        return numOfStates;
    }

    /*Validates the user input string. This makes sure the strings characters are in the alphabet.*/
    public int validate(String sent) {
        int found;
        for (int i = 0; i < sent.length(); i++) {
            found = 0;
            for (int j = 0; j < alphabet.length; j++)
                if (sent.charAt(i) == alphabet[j]) {
                    found = 1;
                }
            if (found != 1) {
                System.out.println("not found");
                return -5;
            }
        }
        return 0;
    }

    /*Runs the FSA machine. The method will determine what state the senquence will land on*/
    public int run(String sent) {
        int currentState = start;
        /*Goes through alphabet until a matching character is found*/
        for (int i = 0; i < sent.length(); i++) {
            for (int j = 0; j < alphabet.length; j++)
                if (sent.charAt(i) == alphabet[j]) {
                    if (transition[currentState][j] != -1) {
                        currentState = transition[currentState][j];
                    } else {
                        return -6;
                    }
                }
        }
        for (int i = 0; i < accepting.length; i++) {
            if (currentState == accepting[i]) {
                return 1;
            }
        }
        return -7;
    }

    /*Reads user input to find the number of states int he FSA*/
    public int setStateNumber(int i) {
        String hold = "";  //Initial string is blank
        //For loop to read string until ; is found
        for (i = i; i < FSA.length() && FSA.charAt(i) != ';'; i++) {
            hold = hold + FSA.charAt(i); //adds valid characters to the string hold
        }
        //converts hold into an int
        numOfStates = Integer.parseInt(hold);
        System.out.println("Number of state : " + numOfStates);
        return i + 1;
    }

    public int setAlphabet(int i) {
        if (i > 0) {
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
        } else
            return i;
    }

    public int setTransitions(int i) {
        if (i > 0) {
            buildTransitionTable();
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
                            for (int k = 0; k < alphabet.length; k++) {
                                if (hold.charAt(l) == alphabet[k]) {
                                    location = k;
                                    System.out.println("Transitioning from " + start + " to " + end + " when " + alphabet[k] + " is found");
                                    transition[start][location] = end;
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
                if (hold.charAt(l) != ')') {
                    return -3;
                } else {

                }
                l = l + 2;
            }
            return i + 1;
        } else
            return i;
    }

    public void buildTransitionTable() {
        transition = new int[numOfStates][alphabet.length];
        for (int i = 0; i < numOfStates; i++) {
            for (int j = 0; j < alphabet.length; j++) {
                transition[i][j] = -1;
            }
        }
    }

    public void printTable() {
        for (int j = 0; j < alphabet.length; j++) {
            System.out.print(alphabet[j]);
        }
        System.out.println("");
        for (int i = 0; i < numOfStates; i++) {
            for (int j = 0; j < alphabet.length; j++) {
                System.out.print(transition[i][j]);
            }
            System.out.println("");
        }
    }

    public int setStart(int i) {
        if (i > 0) {
            String hold = "";
            for (i = i; i < FSA.length() && FSA.charAt(i) != ';'; i++) {
                hold = hold + FSA.charAt(i);
            }
            System.out.println("Start state : " + hold);
            start = Character.getNumericValue(hold.charAt(0));
            return i + 1;
        } else
            return i;
    }

    public int setAccepting(int i) {
        if (i > 0) {
            String hold = "";
            int count = 0;
            for (i = i; i < FSA.length() && FSA.charAt(i) != ';'; i++) {
                if (FSA.charAt(i) == ',') {
                    count++;
                }
                hold = hold + FSA.charAt(i);
            }
            System.out.println("Accepting States : " + hold);
            accepting = new int[count + 1];
            int l = 0;
            for (int j = 0; j < hold.length(); j++) {
                if (hold.charAt(j) != ',') {
                    accepting[l] = Character.getNumericValue(hold.charAt(l));
                    l++;
                }
            }
            return i + 1;
        } else
            return i;
    }
}
