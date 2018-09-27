import java.io.File;
import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
   A simple data source for getting database connections. 
*/
public class SimpleDataSource
{
   private static String url;
   private static String username;
   private static String password;
   private static Connection conn;

   public static void createDataTable(String tableName, String command) throws SQLException {
      if(tableName == "")
      {
         System.out.println(
                 "Usage: java -classpath driver_class_path"
                         + File.pathSeparator
                         + ". TestDB database.properties");
         return;
      }

      else
      {
         try {
            Initiate("database.properties");
         } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
         }
      }
      try {
         conn = SimpleDataSource.getConnection();
      } catch (SQLException e) {
         e.printStackTrace();
      }

      dropTable(tableName);
      Statement stat = conn.createStatement();
      stat.execute(command);
   }

   public static boolean addEntry(String command) throws SQLException {
       try {
          Statement stat = conn.createStatement();
           stat.execute(command);
            return true;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }

public static void viewFullTable(String table) throws SQLException {
   Statement stat = conn.createStatement();
   try {
       //System.out.println(table);
      ResultSet result = stat.executeQuery("SELECT * FROM "+ table);
      ResultSetMetaData rsm = result.getMetaData();
      int cols = rsm.getColumnCount();
      while (result.next()) {
         for (int i = 1; i <= cols; i++)
            System.out.print(result.getString(i) + " ");
         System.out.println("");
      }
   }
   catch (Exception e)
   {
      System.out.println("No Results");
   }

}

   /**
      Initializes the data source.
      @param fileName the name of the property file that 
      contains the database driver, URL, username, and password
   */
   public static void Initiate(String fileName)
         throws IOException, ClassNotFoundException
   {  
      Properties props = new Properties();
      FileInputStream in = new FileInputStream(fileName);
      props.load(in);

      String driver = props.getProperty("jdbc.driver");
      url = props.getProperty("jdbc.url");
      username = props.getProperty("jdbc.username");
      if (username == null) username = "";
      password = props.getProperty("jdbc.password");
      if (password == null) password = "";
      if (driver != null)
         Class.forName(driver);
   }

   /**
      Gets a connection to the database.
      @return the database connection
   */
   public static Connection getConnection() throws SQLException
   {
      return DriverManager.getConnection(url, username, password);
   }

   public static void dropTable(String table) throws SQLException {
      Statement stat = conn.createStatement();
       try {
         stat.execute("DROP TABLE " + table);
      }
      catch (Exception e)
      {
          System.out.println("drop failed");
      }
   }
}











