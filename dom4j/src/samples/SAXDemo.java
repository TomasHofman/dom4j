
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/** A simple test program to demonstrate using SAX to create a DOM4J tree
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.4 $
  */
public class SAXDemo extends AbstractDemo {
    
    public SAXDemo() {
    }
    
    public void run(String[] args) throws Exception {    
        if ( args.length < 1) {
            printUsage( "<XML document URL>" );
            return;
        }

        parse( args[0] );
    }
    
    protected void parse( String xmlFile ) throws Exception {
        URL url = null;
        try {
            url = new URL( xmlFile );
        }
        catch (MalformedURLException e) {
            // try create the URL from a File object
            try {
                File file = new File( xmlFile );
                url = file.toURL();
            }
            catch (MalformedURLException e2) {
                println( "Couldn't create a valid URL or File from: " + xmlFile );
                println( "Caught: " + e.getMessage() + " and " + e2.getMessage() );
                return;
            }
        }
        parse( url );
    }
    
    protected void parse( URL url ) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        process(document);
    }
    
    protected void process(Document document) throws Exception {
        XMLWriter writer = createXMLWriter();
        writer.output(document, System.out);                
    }

    /** A Factory Method to create an <code>XMLWriter</code>
      * instance allowing derived classes to change this behaviour
      */
    protected XMLWriter createXMLWriter() {
        XMLWriter writer = new XMLWriter("  ", true);
        writer.setTrimText(true);
        writer.setExpandEmptyElements(true);
        return writer;
    }
    
    /** The program entry point.
      *
      * @param args the command line arguments
      */
    public static void main(String[] args) {
        run( new SAXDemo(), args );
    }    
}