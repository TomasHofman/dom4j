/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestSetData.java,v 1.1 2001/11/22 12:57:45 jstrachan Exp $
 */

package org.dom4j.datatype;

import java.math.BigInteger;
import java.util.Calendar;
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
import org.dom4j.QName;
import org.dom4j.io.SAXReader;
import org.dom4j.datatype.DatatypeDocumentFactory;


/** Tests setting the value of datatype aware element or attribute value
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 1.1 $
  */
public class TestSetData extends AbstractTestCase {

    private DatatypeDocumentFactory factory = new DatatypeDocumentFactory();
    
    
    public static void main( String[] args ) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite( TestSetData.class );
    }
    
    public TestSetData(String name) {
        super(name);
    }

    // Test case(s)
    //-------------------------------------------------------------------------                    
    public void testAttribute() throws Exception {        
/*        
        QName personName = QName.get( "person", "" );
        QName ageName = QName.get( "age", "" );
*/
        QName personName = factory.createQName( "person" );
        QName ageName = factory.createQName( "age" );
        
        Element person = factory.createElement( personName );
        
        person.addAttribute( ageName, "10" );
        Attribute age = person.attribute( ageName );
        
        assertTrue( "Created DatatypeAttribute", age instanceof DatatypeAttribute );
        
        log( "Found attribute: " + age );
        

        Object data = age.getData();
        Object expected = new BigInteger( "10" );
        
        assertEquals( "Data is correct type", BigInteger.class, data.getClass() );
        
        assertEquals( "Set age correctly", expected, data );
        
        age.setValue( "32" );
        data = age.getData();
        expected = new BigInteger( "32" );
        
        assertEquals( "Set age correctly", expected, data );

/**
 *  not sure if numeric types should be round tripped back to BigDecimal (say)
 *        
        age.setData( new Long( 21 ) );
        data = age.getData();
        expected = new BigInteger( "21" );
        
        assertEquals( "Set age correctly", expected, data );
*/        
        // now lets set an invalid value
        
        try {
            age.setValue( "abc" );
            fail( "Appeared to set an invalid value" );
        }
        catch (IllegalArgumentException e) {
        }
    }    
    
    public void testElement() throws Exception {        
        QName personName = factory.createQName( "person" );
        QName numberOfCarsName = factory.createQName( "numberOfCars" );
        
        Element person = factory.createElement( personName );
        //Element cars = factory.createElement( numberOfCarsName );
        Element cars = person.addElement( numberOfCarsName );
        
        //assertTrue( "Created DatatypeElement", cars instanceof DatatypeElement );
        
        log( "Found element: " + cars );
        
        Object expected = new Short( (short) 10 );
        cars.setData( expected );
        Object data = cars.getData();
        
        assertEquals( "Data is correct type", Short.class, data.getClass() );
        assertEquals( "Set cars correctly", expected, data );
        
        cars.setData( new Short( (short) 32 ) );
        data = cars.getData();
        expected = new Short( (short) 32 );
        
        assertEquals( "Set cars correctly", expected, data );

        cars.setText( "34" );
        data = cars.getData();
        expected = new Short( (short) 34 );
        
        assertEquals( "Set cars correctly", expected, data );

        // now lets set an invalid value        
        try {
            cars.setText( "abc" );
            fail( "Appeared to set an invalid value" );
        }
        catch (IllegalArgumentException e) {
        }
    }    
    
    
    // Implementation methods
    //-------------------------------------------------------------------------                    
    protected void setUp() throws Exception {
        SAXReader reader = new SAXReader();
        Document schema = reader.read( "xml/test/schema/personal.xsd" );
        factory.loadSchema( schema );
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
 * $Id: TestSetData.java,v 1.1 2001/11/22 12:57:45 jstrachan Exp $
 */
