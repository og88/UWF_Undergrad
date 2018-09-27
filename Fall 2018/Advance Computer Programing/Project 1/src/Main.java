import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {

        Class vehicle = Vehicle.class; //Creates a class object for our reflection processes
        createRandom(vehicle, createTable(vehicle));

    }

    /**
     * This method auto generates a SQL table from a jav class.
     * It uses the class name for the table name.
     * Each of the classes variables are the row names.
     * The variable types are the item types as well.
     * @param class1 The method extracts all needed information form this class.
     */
    static Field[] createTable(Class class1)
    {
        Writer write1 = new Writer("dbOperations.log");
        write1.startNew("DB Log \n\n");

        String className = class1.getName();

        Field[] fields = class1.getDeclaredFields();

        String createTable = "CREATE TABLE " + className + "(\n";
        write1.add("Table " + className + " Created.\n\n");
        for(Field field: fields)
        {
            if(field.getType().getName() == "java.lang.String")
            {
                createTable = createTable + field.getName() + " String,\n";
            }
            else {
                createTable = createTable + field.getName() + " " + field.getType().getName() + ",\n";
            }
        }
        createTable = createTable + ");";

        System.out.print(createTable);
        return fields;
    }

    public static void createRandom(Class class1, Field[] fields) {
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
                System.out.println("Invalid Weight");
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

    public static void addToDataBase(Vehicle vehicle, Class class1, Field[] fields)
    {
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
                " , " + vehicle.getEngineSize() + ", "+vehicle.isImport()+")";
        write1.add("Success : Program added new entry Successfully\n" +
                "**************************************************\n");
        System.out.println(Command);
    }
}
