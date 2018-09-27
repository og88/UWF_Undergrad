import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Random;

public class Main {

    /**
     *
     * @param args Is not used for this program
     * @throws SQLException We need to handle SQL exceptions for this program.
     */
    public static void main(String[] args) throws SQLException {

        Class vehicle = Vehicle.class; //Creates a class object for our reflection processes
        createRandom(vehicle, createTable(vehicle));  //Creates 10 random vehicles and adds them to a database
        SimpleDataSource.viewFullTable(vehicle.getName());  //Prints to screen all data from a database

    }

    /**
     * This method auto generates a SQL table from a jav class.
     * It uses the class name for the table name.
     * Each of the classes variables are the row names.
     * The variable types are the item types as well.
     * @param class1 The method extracts all needed information form this class.
     */
    public static Field[] createTable(Class class1) throws SQLException {
        String className = class1.getName();  //Gets teh name of the class

        Field[] fields = class1.getDeclaredFields();  //Retrieves all private fields. These will be used for our table.

        String createTable = "CREATE TABLE " + className + " (";  //Autonomously build a create table.

        for(Field field: fields)    //Adds the Rows we will uae for the table. The program converts the java types into equivalent SQL types.
        {
            if(field.getType().getName() == "java.lang.String")
            {
                createTable = createTable + field.getName() + " CHAR(15),";
                System.out.print(field.getName() + " ");
            }
            else if(field.getType().getName() == "boolean")
            {
                createTable = createTable + field.getName() + " SMALLINT ";
                System.out.print(field.getName()  + " ");
            }
            else if(field.getType().getName() == "double")
            {
                createTable = createTable + field.getName() + " DOUBLE, ";
                System.out.print(field.getName() + " ");
            }
            else {
                createTable = createTable + field.getName() + " " + field.getType().getName() + ", ";
                System.out.print(field.getName()  + " ");
            }
        }
        System.out.print("\n");

        createTable = createTable + ")";

        SimpleDataSource.createDataTable(className, createTable);  //Executes database code to initialize the table.


        Writer write1 = new Writer("dbOperations.log");   //Writer class adds entries to our Database log file
        write1.startNew("DB Log \n\n");
        write1.add("Table " + className + " Created.\n\n");

        return fields;   //return the field for use in adding entries
    }


    /**
     *
     * @param class1
     * @param fields
     * @throws SQLException
     */
    public static void createRandom(Class class1, Field[] fields) throws SQLException {
        Random rand = new Random();
        Writer write1 = new Writer("dbOperations.log");

        for (int i = 0; i < 10; i++)
        {
            Vehicle v = new Vehicle();
            v.setMake();
            v.setModel();
            int n = rand.nextInt(2500) + 1501;
            while (v.setWeight(n) == 0)
            {
                write1.add("Warning : Program tried to add an Invalid weight\nValue " + n + " for Type: " + v.getModel()+"\n\n");
                n = rand.nextInt(2500) + 1501;
            }
            n = rand.nextInt(6) + 1;
            v.setEngineSize(n);
            v.getEngineSize();
            n = rand.nextInt(2);
            if(n == 1) { v.setImport(true); }
            else{v.setImport(false);}

            addToDataBase(v, class1, fields);
        }
    }

    /**
     *
     * @param vehicle
     * @param class1
     * @param fields
     * @throws SQLException
     */

    public static void addToDataBase(Vehicle vehicle, Class class1, Field[] fields) throws SQLException {
        Writer write1 = new Writer("dbOperations.log");

        String Columns = "( ";
        for(int i = 0; i < fields.length; i++)
        {
            if(i == fields.length - 1)
            {
                Columns = Columns + fields[i].getName() + ") \n";
            }
            else {Columns = Columns + fields[i].getName() + ", ";}
        }
        String Command = "INSERT INTO " + class1.getName() + " " + Columns +
                "VALUES ('"+vehicle.getMake()+"' , '"+ vehicle.getModel()+"' , "+ vehicle.getWeight() +
                " , " + vehicle.getEngineSize() + ", " + vehicle.isImport() + " )";
        if(SimpleDataSource.addEntry(Command)) {
            write1.add("Success : Program added new entry Successfully\n" +
                    "**************************************************\n");
        }
        else
        {
            write1.add("EROOR ADDING ENTRY " + Command + "\n ********************************************");
        }
    }
}
