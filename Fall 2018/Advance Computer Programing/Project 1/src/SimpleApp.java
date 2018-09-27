import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.lang.Boolean;


/**
   Tests a database installation by creating and querying
   a sample table. Call this program as
   java -classpath driver_class_path;. TestDB database.properties
*/
/*public class SimpleApp
{
   public static void main(String[] args) throws Exception
   {   
      if (args.length == 0)
      {   
         /*System.out.println(
               "Usage: java -classpath driver_class_path" 
               + File.pathSeparator 
               + ". TestDB database.properties");
         return;*/
         /*System.out.println("args[0] = " + "TestDB");
         SimpleDataSource.init("database.properties");
      }
      else 
		{
		   System.out.println("args[0] = " + args[0]);
         SimpleDataSource.init(args[0]);
		}
      
      Connection conn = SimpleDataSource.getConnection();
      Statement stat = conn.createStatement();     
 	   try {  
		  stat.execute("DROP TABLE Vehicle"); 
      }
	   catch (Exception e)
		{ System.out.println("drop failed"); }      

      try
      {
   
         stat.execute("CREATE TABLE Vehicle (Make CHAR(10), Model CHAR(10), Weight DOUBLE, EngineSize DOUBLE, IsImport SMALLINT )");
         stat.execute("INSERT INTO Vehicle VALUES ('FORD', 'SUV', 3500, 6, 1)");  
			System.out.println("after inserts");
         ResultSet result = stat.executeQuery("SELECT * FROM Vehicle");
			ResultSetMetaData rsm = result.getMetaData();
			int cols = rsm.getColumnCount();
			  while(result.next())
			  {
			    for(int i = 1; i <= cols; i++)
               System.out.print(result.getString(i)+" ");
             System.out.println("");      
			  }
			try {  
		     stat.execute("DROP TABLE Vehicle"); 
         }
			catch (Exception e)
			{ System.out.println("drop failed"); }    
		}
      finally
      {
         conn.close();
			System.out.println("dropped Table vehicle, closed connection and ending program");  
      }
   }
}
*/