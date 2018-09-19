
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Vehicle v1 = new Vehicle();
        System.out.println(v1.getMake());
        v1.setMake("Ford");
        System.out.println(v1.getMake());
        Writer write1 = new Writer("dbOperations.log");
        write1.startNew("DB Log \n");
        write1.add("Test");

    }
}
