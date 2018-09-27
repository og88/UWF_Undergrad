import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {

        Class vehicle = Vehicle.class; //Creates a class object for our reflection processes

        createTable(vehicle);

    }

    /**
     * This method auto generates a SQL table from a jav class.
     * It uses the class name for the table name.
     * Each of the classes variables are the row names.
     * The variable types are the item types as well.
     * @param class1 The method extracts all needed information form this class.
     */
    static void createTable(Class class1) throws IOException, ClassNotFoundException, SQLException {
        Writer write1 = new Writer("dbOperations.log");
        write1.startNew("DB Log \n");
        write1.add("Test\n");

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

        SimpleDataSource.init(className);
        Connection conn = SimpleDataSource.getConnection();
        Statement stat = conn.createStatement();
        stat.execute(createTable);
        conn.close();


    }
}
