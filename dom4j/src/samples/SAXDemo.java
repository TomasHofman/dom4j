/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: SAXDemo.java,v 1.7 2001/01/24 16:52:13 jstrachan Exp $
 */


import java.net.URL;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/** A simple test program to demonstrate using SAX to create a DOM4J tree
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.7 $
  */
public class SAXDemo extends AbstractDemo {
    
    public static void main(String[] args) {
        run( new SAXDemo(), args );
    }    
    
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
        URL url = getURL( xmlFile );
        if ( url != null ) {
            parse( url );
        }
    }
    
    protected void parse( URL url ) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        process(document);
    }
    
    protected void process(Document document) throws Exception {
        XMLWriter writer = createXMLWriter();
        writer.write(document, System.out);                
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
    
}




/*
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "DOM4J" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of MetaStuff, Ltd.  For written permission,
 *    please contact dom4j-info@metastuff.com.
 *
 * 4. Products derived from this Software may not be called "DOM4J"
 *    nor may "DOM4J" appear in their names without prior written
 *    permission of MetaStuff, Ltd. DOM4J is a registered
 *    trademark of MetaStuff, Ltd.
 *
 * 5. Due credit should be given to the DOM4J Project
 *    (http://dom4j.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY METASTUFF, LTD. AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * METASTUFF, LTD. OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * $Id: SAXDemo.java,v 1.7 2001/01/24 16:52:13 jstrachan Exp $
 */
