import java.util.ArrayList;
import java.util.ArrayList;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSOutput;
import java.io.*;
/**
   This program parses an XML file containing an item list.
   It prints out the items that are described in the XML file.
*/
public class ItemListParserDemo
{
   public static void main(String[] args) throws Exception
   {
      ItemListParser parser = new ItemListParser();
      ArrayList<LineItem> items = parser.parse("items.xml");
      for (LineItem anItem : items)
         System.out.println(anItem.format());
	   items.add(new LineItem(new Product("Toaster", 29.95), 3));
      items.add(new LineItem(new Product("Bicycle", 124.95), 5));
		items.add(new LineItem(new Product("Hair dryer", 24.95), 1));

      ItemListBuilder builder = new ItemListBuilder();
      Document doc = builder.build(items);         
      DOMImplementation impl = doc.getImplementation();
      DOMImplementationLS implLS 
            = (DOMImplementationLS) impl.getFeature("LS", "3.0");
      LSSerializer ser = implLS.createLSSerializer();
      LSOutput output=implLS.createLSOutput();
		
		/*
      OutputStream outputStream = 
           new FileOutputStream(new File("z:/output.xml"));
      output.setByteStream(outputStream);
      output.setEncoding("UTF-8");
      ser.write(doc,output);   */  		
		
      String out = ser.writeToString(doc);      
      PrintWriter outf = new PrintWriter("test.xml");
		outf.println(out);
		outf.close();
      System.out.println(out);

   }
}
