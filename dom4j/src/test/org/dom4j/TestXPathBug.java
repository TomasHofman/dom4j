/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestXPathBug.java,v 1.2 2002/02/20 02:42:27 jstrachan Exp $
 */

package org.dom4j;

import java.util.Iterator;
import java.util.List;

import junit.framework.*;
import junit.textui.TestRunner;

import org.dom4j.io.SAXReader;

/** A test harness to test XPath expression evaluation in DOM4J
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.2 $
  */
public class TestXPathBug extends AbstractTestCase {
    
    public static void main( String[] args ) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite( TestXPathBug.class );
    }
    
    public TestXPathBug(String name) {
        super(name);
    }

    // Test case(s)
    //-------------------------------------------------------------------------                    
    public void testXPaths() throws Exception {        
        SAXReader reader = new SAXReader();
        Document document = reader.read( "xml/rabo1ae.xml" );
        Element root = (Element) document.selectSingleNode( "/m:Msg/m:Contents/m:Content" );
        
        assertTrue( "root is not null", root != null );
        
        Namespace ns = root.getNamespaceForPrefix( "ab" );
        
        assertTrue( "Found namespace", ns != null );
        
        System.out.println( "Found: " + ns.getURI() );
/*        
        Element element = (Element) root.selectSingleNode( "ab:RaboPayLoad" );
        
        assertTrue( "element is not null", element != null );
        
        String value = element.valueOf( "ab:RateType" );
        
        assertEquals( "RateType is correct", "CRRNT", value );
*/
    }
    
    /** A bug found by Rob Lebowitz
     */
    public void testRobLebowitz() throws Exception {        
        String text = "<ul>"
            + "    <ul>"
            + "        <li/>"
            + "            <ul>"
            + "                <li/>"
            + "            </ul>"
            + "        <li/>"
            + "    </ul>"
            + "</ul>";
        
        Document document = DocumentHelper.parseText( text );
        List lists = document.selectNodes( "//ul | //ol" );
        
        int count = 0;
        for (int i = 0; i < lists.size(); i++) {
            Element list = (Element)lists.get(i);
            List nodes = list.selectNodes("ancestor::ul");
            if ((nodes != null) && (nodes.size() > 0)) {
                continue;
            }
            nodes = list.selectNodes("ancestor::ol");
            if ((nodes != null) && (nodes.size() > 0)) {
                continue;
            }
        }
    }
    
    /** A bug found by Stefan which results in 
     * IndexOutOfBoundsException for empty results
     */
    public void testStefan() throws Exception {
        String text = "<foo>hello</foo>";
        Document document = DocumentHelper.parseText( text );
        XPath xpath = DocumentHelper.createXPath( "/x" );
        Object value = xpath.evaluate( document );
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
 * $Id: TestXPathBug.java,v 1.2 2002/02/20 02:42:27 jstrachan Exp $
 */
