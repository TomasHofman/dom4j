
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.dom4j.*;
import org.dom4j.io.BinaryWriter;

/** Creates a DOM4J tree from an XML URL using SAX then compiles it into a binary file
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.3 $
  */
public class Compile extends SAXDemo {
    
    protected String outputFileName;
    
    
    public Compile() {
    }
        
    /** The program entry point.
      *
      * @param args the command line arguments
      */
    public static void main(String[] args) {
        run( new Compile(), args );
    }    
    
    public void run(String[] args) throws Exception {    
        if ( args.length < 2 ) {
            printUsage( "<XML document URL> <Output Binary XML File>" );
            return;
        }

        String xmlFile = args[0];
        outputFileName = args[1];

        println( "Compiling text XML file: " + xmlFile + " into binary file: " + outputFileName );
        
        parse( xmlFile );
    }
    
    protected void process(Document document) throws Exception {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream( new FileOutputStream( outputFileName ) );
            BinaryWriter writer = new BinaryWriter();
            writer.write( document, out );
        }
        finally {
            if ( out != null ) {
                try {
                    out.close();
                }
                catch (Exception e) {
                }
            }
        }
    }
}