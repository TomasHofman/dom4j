/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestDataTypes.java,v 1.3 2001/08/14 15:17:25 jstrachan Exp $
 */

package org.dom4j.schema;

import java.math.BigDecimal;
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
import org.dom4j.io.SAXReader;
import org.dom4j.schema.SchemaDocumentFactory;


/** Test harness to test the various data types supported in the 
  * XML Schema Data Type integration. 
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 1.3 $
  */
public class TestDataTypes extends AbstractDataTypeTest {

    public static void main( String[] args ) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite( TestDataTypes.class );
    }
    
    public TestDataTypes(String name) {
        super(name);
    }

    // Test case(s)
    //-------------------------------------------------------------------------                    
    
/*    
    public void testDuration() throws Exception {        
        testNodes( "//durationTag", Calendar.class );
    }
    public void testgMonthDay() throws Exception {        
        testNodes( "//gMonthDayTag", Calendar.class );
    }
    public void testgDay() throws Exception {        
        testNodes( "//gDayTag", Calendar.class );
    }
    public void testgMonth() throws Exception {        
        testNodes( "//gMonthTag", Calendar.class );
    }
*/
    
    public void testDate() throws Exception {        
        testNodes( "//dateTag", Calendar.class );
    }
    
    public void testTime() throws Exception {        
        testNodes( "//timeTag", Calendar.class );
    }
    public void testDateTime() throws Exception {        
        testNodes( "//dateTimeTag", Calendar.class );
    }
    
    public void testgYearMonth() throws Exception {        
        testNodes( "//gYearMonthTag", Calendar.class );
    }
    public void testgYear() throws Exception {        
        testNodes( "//gYearTag", Calendar.class );
    }
    

    public void testBoolean() throws Exception {        
        testNodes( "//booleanTag", Boolean.class );
    }
   
    public void testBase64Binary() throws Exception {        
        testNodes( "//base64BinaryTag", byte[].class );
    }
    public void testHexBinary() throws Exception {        
        testNodes( "//hexBinaryTag", byte[].class  );
    }

    
    
    
    // Number types
    
    public void testFloat() throws Exception {        
        testNodes( "//floatTag", Float.class );
    }
    public void testDouble() throws Exception {        
        testNodes( "//doubleTag", Double.class );
    }

    
    public void testDecimal() throws Exception {        
        testNodes( "//decimalTag", BigDecimal.class );
    }

    public void testInteger() throws Exception {        
        testNodes( "//integerTag", BigInteger.class );
    }

    
    public void testNonPositiveInteger() throws Exception {        
        testNodes( "//nonPositiveIntegerTag", BigInteger.class );
    }
    
    public void testNegativeInteger() throws Exception {        
        testNodes( "//negativeIntegerTag", BigInteger.class );
    }
    
    public void testLong() throws Exception {        
        testNodes( "//longTag", Long.class );
    }
    public void testInt() throws Exception {        
        testNodes( "//intTag", Integer.class );
    }
    public void testShort() throws Exception {        
        testNodes( "//shortTag", Short.class );
    }
    public void testByte() throws Exception {        
        testNodes( "//byteTag", Byte.class );
    }
    
    public void testNonNegativeInteger() throws Exception {        
        testNodes( "//nonNegativeIntegerTag", BigInteger.class );
    }
    
    public void testUnsignedLong() throws Exception {        
        testNodes( "//unsignedLongTag", BigInteger.class );
    }
    
    public void testUnsignedInt() throws Exception {        
        testNodes( "//unsignedIntTag", Long.class );
    }
    public void testUnsignedShort() throws Exception {        
        testNodes( "//unsignedShortTag", Integer.class );
    }
    public void testUnsignedByte() throws Exception {        
        testNodes( "//unsignedByteTag", Short.class );
    }
    
    public void testPositiveInteger() throws Exception {        
        testNodes( "//positiveIntegerTag", BigInteger.class );
    }

    // Implementation methods
    //-------------------------------------------------------------------------                        
    protected void setUp() throws Exception {
        DocumentFactory factory = SchemaDocumentFactory.getInstance();
        SAXReader reader = new SAXReader( factory );
        document = reader.read( "xml/schema/test.xml" );
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
 * $Id: TestDataTypes.java,v 1.3 2001/08/14 15:17:25 jstrachan Exp $
 */
