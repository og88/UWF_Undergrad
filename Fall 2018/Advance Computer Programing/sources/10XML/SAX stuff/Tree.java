// Tree.java
// Using the SAX Parser to generate a tree diagram.

import java.io.*;
import org.xml.sax.*;  // for HandlerBase class
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import org.xml.sax.helpers.DefaultHandler; 


public class Tree extends HandlerBase {
//public class Tree extends HandlerBase {
   private int indent = 0;  // indentation counter

   // returns the spaces needed for indenting
   private String spacer( int count )
   {
      String temp = "";

      for ( int i = 0; i < count; i++ )
         temp += "  ";

      return temp;
   }

   // method called before parsing
   // it provides the document location
   public void setDocumentLocator( Locator loc )
   {
      System.out.println( "URL: " + loc.getSystemId() );
   }

   // method called at the beginning of a document
   public void startDocument() throws SAXException
   {
      System.out.println( "[ document root ]" );
   }

   // method called at the end of the document
   public void endDocument() throws SAXException
   {
      System.out.println( "[ document end ]" );
   }

   // method called at the start tag of an element
   public void startElement(String name,
      AttributeList attributes ) throws SAXException
   {
	   // System.out.println("found element");
      System.out.println( spacer( indent++ ) +
                          "+-[ element : " + name + " ]");

      if ( attributes != null )

         for ( int i = 0; i < attributes.getLength(); i++ )
            System.out.println( spacer( indent ) +
               "+-[ attribute : " + attributes.getName( i ) +
               " ] \"" + attributes.getValue( i ) + "\"" );
   }

   // method called at the end tag of an element
   public void endElement( String name ) throws SAXException
   {
      indent--;
   }

   // method called when a processing instruction is found
   public void processingInstruction( String target,
      String value ) throws SAXException
   {
      System.out.println( spacer( indent ) +
         "+-[ proc-inst : " + target + " ] \"" + value + "\"" );
   }

   // method called when characters are found
   public void characters( char buffer[], int offset,
      int length ) throws SAXException
   {
      if ( length > 0 ) {
         String temp = new String( buffer, offset, length );

         System.out.println( spacer( indent ) +
                             "+-[ text ] \"" + temp + "\"" );
      }
   }

   // method called when ignorable whitespace is found
   public void ignorableWhitespace( char buffer[],
      int offset, int length )
   {
      if ( length > 0 ) {
         System.out.println( spacer( indent ) + "+-[ ignorable ]" );
      }
   }

   // method called on a non-fatal (validation) error
   public void error( SAXParseException spe ) 
      throws SAXParseException
   {
      // treat non-fatal errors as fatal errors
      throw spe;
   }

   // method called on a parsing warning
   public void warning( SAXParseException spe )
      throws SAXParseException
   {
      System.err.println( "Warning: " + spe.getMessage() );
   }

   // main method
   public static void main( String args[] )
   {
      boolean validate = false;
      String filename = "spacing2.xml";
       //String filename = "stockquote.wsdl";

      SAXParserFactory saxFactory =
         SAXParserFactory.newInstance();

      saxFactory.setValidating( validate );

      try {
         SAXParser saxParser = saxFactory.newSAXParser();
         saxParser.parse( new File( filename ), new Tree() );
      }
      catch ( SAXParseException spe ) {
         System.err.println( "Parse Error: " + spe.getMessage() );
      }
      catch ( SAXException se ) {
         se.printStackTrace();
      }
      catch ( ParserConfigurationException pce ) {
         pce.printStackTrace();
      }
      catch ( IOException ioe ) {
         ioe.printStackTrace();
      }

      System.exit( 0 );
   }
}
