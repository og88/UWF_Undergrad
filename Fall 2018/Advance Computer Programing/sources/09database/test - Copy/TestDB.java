import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
   Tests a database installation by creating and querying
   a sample table. Call this program as
   java -classpath driver_class_path;. TestDB database.properties
*/
public class TestDB 
{
   public static void main(String[] args) throws Exception
   {   
      if (args.length == 0)
      {   
         System.out.println(
               "Usage: java -classpath driver_class_path" 
               + File.pathSeparator 
               + ". TestDB database.properties");
         return;
      }
      else 
		{
		   System.out.println("args[0] = " + args[0]);
         SimpleDataSource.init(args[0]);
		}
      
      Connection conn = SimpleDataSource.getConnection();
      try
      {
         Statement stat = conn.createStatement();
         System.out.println("Creating Romeo: 27, Juliet: 25, Tom: 64, Dick: 55, and Harry: 33");
         stat.execute("CREATE TABLE Test2 (Name CHAR(20),Age CHAR(5))");
         stat.execute("INSERT INTO Test2 VALUES ('Romeo','27')");
         stat.execute("INSERT INTO Test2 VALUES ('Juliet','25')");
         stat.execute("INSERT INTO Test2 VALUES ('Tom','64')");
         stat.execute("INSERT INTO Test2 VALUES ('Dick','55')");
         stat.execute("INSERT INTO Test2 VALUES ('Harry','33')");
         ResultSet result = stat.executeQuery("SELECT * FROM Test2");
			  
			System.out.println("after inserts");
			ResultSetMetaData rsm = result.getMetaData();
			int cols = rsm.getColumnCount();
			  while(result.next())
			  {
			    for(int i = 1; i <= cols; i++)
               System.out.print(result.getString(i)+" ");
             System.out.println("");      
			  }
			try {  
		     stat.execute("DROP TABLE Test2"); 
         }
			catch (Exception e)
			{ System.out.println("drop failed"); }    
		}
      finally
      {
         conn.close();
			System.out.println("dropped Table Test2, closed connection and ending program");  
      }
   }
}
