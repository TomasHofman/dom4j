/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestBackedList.java,v 1.12 2004/02/26 22:24:30 maartenc Exp $
 */

package org.dom4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.dom4j.Node;

import org.dom4j.io.XMLWriter;

/** A test harness to test the backed list feature of DOM4J
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.12 $
  */
public class TestBackedList extends AbstractTestCase {

    public static void main( String[] args ) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite( TestBackedList.class );
    }
    
    public TestBackedList(String name) {
        super(name);
    }

    // Test case(s)
    //-------------------------------------------------------------------------                    
    public void testXPaths() throws Exception {        
        Element element = (Element) document.selectSingleNode( "/root" );
        mutate(element);
        element = (Element) document.selectSingleNode( "//author" );
        mutate(element);
    }
    
    public void testAddRemove() throws Exception {
        Element parentElement = (Element) document.selectSingleNode( "/root" );
        List children = parentElement.elements(); 
        int lastPos = children.size() - 1; 
        Element child = (Element) children.get(lastPos); 
        try {
            // should throw an exception cause we cannot add same child twice
            children.add(0, child); 
            fail();
        } catch (IllegalAddException e) {}
    }
    
    public void testAddWithIndex() throws Exception {
        DocumentFactory factory = DocumentFactory.getInstance();
        
        Element root = (Element) document.selectSingleNode( "/root" );
        List children = root.elements();  // return a list of 2 author elements
        
        assertEquals(2, children.size());
        
        children.add(1, factory.createElement("dummy1"));
        children = root.elements();
        
        assertEquals(3, children.size());
        
        children = root.elements("author");
        
        assertEquals(2, children.size());
        
        children.add(1, factory.createElement("dummy2"));
        
        children = root.elements();
        
        assertEquals(4, children.size());
        assertEquals("dummy1", ((Node) children.get(1)).getName());
        assertEquals("dummy2", ((Node) children.get(2)).getName());
        
        /*
         * Some tests for issue reported at
         * http://sourceforge.net/tracker/index.php?func=detail&aid=853714&group_id=16035&atid=316035
         */
        children.add(children.size(), factory.createElement("dummy3"));
        children = root.elements("author");
        children.add(children.size(), factory.createElement("dummy4"));
    }
    
    public void testSort() {
//        DocumentFactory factory = DocumentFactory.getInstance();
//        Element root = (Element) document.selectSingleNode( "/root" );
//        root.elements().add(factory.createElement("aaa"));
//        
//        List elements = root.elements();
//        Collections.sort(elements, new Comparator() {
//            public int compare(Object obj1, Object obj2) {
//                Node node1 = (Node) obj1;
//                Node node2 = (Node) obj2;
//                return node1.getName().compareTo(node2.getName());
//            }
//        }); 
//        
//        List sortedElements = root.elements();
//        assertEquals("aaa", ((Node) sortedElements.get(0)).getName());
    }
        
    // Implementation methods
    //-------------------------------------------------------------------------                    
    protected void mutate(Element element) throws Exception {
        DocumentFactory factory = DocumentFactory.getInstance();
        
        List list = element.elements();
        list.add(factory.createElement("last" ));
        list.add(0, factory.createElement("first" ));
        
        List list2 = element.elements();
        
        assertTrue( "Both lists should contain same number of elements", list.size() == list2.size() );
        
        XMLWriter writer = new XMLWriter( System.out );
        
        log( "Element content is now: " + element.content() );
        writer.write( element );
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
 * $Id: TestBackedList.java,v 1.12 2004/02/26 22:24:30 maartenc Exp $
 */
