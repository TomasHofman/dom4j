/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestManualSchema.java,v 1.3 2001/05/30 14:17:14 jstrachan Exp $
 */

package org.dom4j.schema;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.*;
import junit.textui.TestRunner;

import org.dom4j.AbstractTestCase;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.schema.SchemaDocumentFactory;


/** Test harness for the XML Schema Data Type integration. These tests
  * manually load the schemas
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 1.3 $
  */
public class TestManualSchema extends AbstractTestCase {

    protected static boolean VERBOSE = true;
    
    public static void main( String[] args ) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite( TestManualSchema.class );
    }
    
    public TestManualSchema(String name) {
        super(name);
    }

    // Test case(s)
    //-------------------------------------------------------------------------                    
    public void testIntAttribute() throws Exception {        
        testSchema( "//person/@x", Integer.class );
    }
    
    public void testIntElement() throws Exception {        
        testSchema( "//person/salary", Integer.class );
    }
    
    public void testString() throws Exception {        
        testSchema( "//person/note", String.class );
    }

/*
 * these don't yet work due to a bug in Sun's xsdlib 
 *
 
    public void testDate() throws Exception {        
        testSchema( "//person/@d", Date.class );
    }
    
    public void testInteger() throws Exception {        
        testSchema( "//person/@age", Integer.class );
    }
*/
        
    // Implementation methods
    //-------------------------------------------------------------------------                        
    protected void testSchema(String xpath, Class type) {
        List list = document.selectNodes( xpath );
        
        log( "Searched path: " + xpath + " found: " + list.size() + " result(s)" );
        
        if ( VERBOSE ) {
            log( "" );
            log( "xpath: " + xpath );
            log( "" );
            log( "results: " + list );
            log( "" );
        }
        
        assert( "Results are not empty", ! list.isEmpty() );
        
        for ( Iterator iter = list.iterator(); iter.hasNext(); ) {
            Node node = (Node) iter.next();
            if ( node instanceof Element ) {
                Element element = (Element) node;
                testDataType( element, element.getData(), type );
            }
            else if ( node instanceof Attribute ) {
                Attribute attribute = (Attribute) node;
                testDataType( attribute, attribute.getData(), type );
            }
            else {
                assert( "Did not find an attribute or element: " + node, false );
            }
        }
    }
    
    protected void testDataType(Node node, Object data, Class type) {
        assert( "Data object is not null", data != null );
        
        if ( VERBOSE ) {
            log( "found: " + data + " type: " + data.getClass().getName() + " required type: " + type.getName() );
            log( "node: " + node );
        }
        
        assert( 
            "Data object is of the correct type. Expected: " 
                + type.getName() 
                + " and found: " + data.getClass().getName(), 
            type.isAssignableFrom( data.getClass() ) 
        );
    }

    
    protected void setUp() throws Exception {
        super.setUp();

        DocumentFactory factory = loadDocumentFactory();
        
        SAXReader reader = new SAXReader( factory );
        document = reader.read( "xml/schema/personal.xml" );
    }
    
    protected DocumentFactory loadDocumentFactory() throws Exception {
        SchemaDocumentFactory factory = new SchemaDocumentFactory();
        
        SAXReader reader = new SAXReader();
        Document schemaDocument = reader.read( "xml/schema/personal.xsd" );
        factory.loadSchema( schemaDocument );
        return factory;
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
 * $Id: TestManualSchema.java,v 1.3 2001/05/30 14:17:14 jstrachan Exp $
 */
