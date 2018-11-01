
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Random;

public class Main {	
	
    /**
     *This program will generate a SQL data table using reflection an a class file
     * @param args Is not used for this program
     * @throws SQLException We need to handle SQL exceptions for this program.
     */
    public static void main(String[] args) throws SQLException {

        Class vehicle = Vehicle.class; //Creates a class object for our reflection processes
        try {
			createRandom(vehicle, createTable(vehicle));
		} catch (SQLException e) {
			e.printStackTrace();
		}  //Creates 10 random vehicles and adds them to a database
        
        if(!SimpleDataSource.viewFullTable(vehicle.getName()))  //Prints to screen all data from a database
        {
        	System.out.println("Error reading tale");
        }
        
        try {
			SimpleDataSource.wr.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * This method auto generates a SQL table from a java class.
     * It uses the class name for the table name.
     * Each of the classes variables are the row names.
     * The variable types are the item types as well.
     * @param class1 The method extracts all needed information form this class.
     */
    public static Field[] createTable(Class class1) throws SQLException {
        String className = class1.getName();  //Gets the name of the class

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
        return fields;   //return the field for use in adding entries
    }


    /**
     * This method creates 10 random vehicle objects. Each of the vehicle object will be transfered into a class, where theeir information will be extracted by reflection.
     * @param class1 Parent class used for the table information
     * @param fields The variables name for the objects we will be inserting
     */
    public static void createRandom(Class class1, Field[] fields)  {
        Random rand = new Random();

        for (int i = 0; i < 10; i++)
        {
            Vehicle v = new Vehicle();
            v.setMake();
            v.setModel();
            int n = rand.nextInt(2500) + 1501;
            while (v.setWeight(n) == 0)
            {
                SimpleDataSource.wr.add("--Warning : Program tried to add an Invalid weight\n--Value " + n + " for Type: " + v.getModel()+"\n");
                n = rand.nextInt(2500) + 1501;
            }
            n = rand.nextInt(6) + 1;
            v.setEngineSize(n);
            v.getEngineSize();
            n = rand.nextInt(2);
            if(n == 1) { v.setImport(true); }
            else{v.setImport(false);}

            try {
				addToDataBase(v, class1, fields);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
        }
    }

    /**
     * Generates a query to add items to the database. The methods uses reflection to extract the data from the class and embeds it into the query for the database
     * @param vehicle Vehicle class we will be extracting information from
     * @param class1  The original class we extracted the information needed for the table
     * @param fields The variables in the class that are the column names
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */

    public static void addToDataBase(Vehicle vehicle, Class class1, Field[] fields) throws IllegalArgumentException, IllegalAccessException  {
        String Columns = "( ";
        
        //For loop to build the start of our string (Insert (Make, Model....etc))
        for(int i = 0; i < fields.length; i++)
        {
            if(i == fields.length - 1)
            {
                Columns = Columns + fields[i].getName() + ") \n";
            }
            else {Columns = Columns + fields[i].getName() + ", ";}
        }
        
        
        String Command = "INSERT INTO " + class1.getName() + " " + Columns + "VALUES ('";
        
        	Field f = null;   //A field used by reflection to get the values of the private variables in our vehicle class
			try {
				f = vehicle.getClass().getDeclaredField("Make");
			} catch (NoSuchFieldException | SecurityException e5) {
				e5.printStackTrace();
			}
			f.setAccessible(true);
			Command = Command +  (String) f.get(vehicle) + "' , '";
                
                
    		try {
				f = vehicle.getClass().getDeclaredField("Model");
			} catch (NoSuchFieldException | SecurityException e4) {
				e4.printStackTrace();
			}
    		f.setAccessible(true);
            Command = Command + (String) f.get(vehicle) +"' , ";
                
            try {
				f = vehicle.getClass().getDeclaredField("Weight");
			} catch (NoSuchFieldException | SecurityException e3) {
				e3.printStackTrace();
			}
            f.setAccessible(true);
            Command = Command + (double) f.get(vehicle) +" , ";
                
            try {
				f = vehicle.getClass().getDeclaredField("EngineSize");
			} catch (NoSuchFieldException | SecurityException e2) {
				e2.printStackTrace();
			}
    		f.setAccessible(true);
            Command = Command + (double) f.get(vehicle) + ", ";
                
            try {
				f = vehicle.getClass().getDeclaredField("Import");
			} catch (NoSuchFieldException | SecurityException e1) {
				e1.printStackTrace();
			}
    		f.setAccessible(true);
    		
    		if((boolean) f.get(vehicle))  //Checks to see whether the car is an import or not and sets appropriate value for it
    		{
               Command = Command + 1 + " )";
    		}
    		else
    		{
    			Command = Command + 0 + " )";
    		}
                
        try {
			if(!SimpleDataSource.addEntry(Command)) {  //Tells the user if an error occurs
			    System.out.println("ERROR ADDING ENTRY " + Command);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    
}
