/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: ValidatorDemo.java,v 1.1 2001/08/20 14:57:21 jstrachan Exp $
 */

package validate;

import AbstractDemo;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.XMLErrorHandler;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/** Parses a document using Xerces and an XML Schema.
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 1.1 $
  */
public class ValidatorDemo extends AbstractDemo {
    
    public static void main(String[] args) {
        run( new ValidatorDemo(), args );
    }    
    
    public ValidatorDemo() {
    }
    
    public void run(String[] args) throws Exception {    
        if ( args.length < 1) {
            printUsage( "no XML document URL specified" );
            return;
        }
        
        parse( args[0] );
    }    
    
    protected Document parse(String uri) throws Exception {
        SAXReader reader = new SAXReader();
        
        reader.setValidation(true);
        
        // specify the schema to use
        reader.setProperty( 
            "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", 
            "personal.xsd" 
        );
    
        // add an error handler which turns any errors into XML
        XMLErrorHandler errorHandler = new XMLErrorHandler();
        reader.setErrorHandler( errorHandler );

        // now lets parse the document
        Document document = reader.read(uri);

        // now lets output the errors as XML        
        XMLWriter writer = new XMLWriter( OutputFormat.createPrettyPrint() );
        writer.write( errorHandler.getErrors() );        
        
        return document;
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
 * $Id: ValidatorDemo.java,v 1.1 2001/08/20 14:57:21 jstrachan Exp $
 */
