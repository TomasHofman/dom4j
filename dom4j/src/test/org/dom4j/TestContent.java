/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestContent.java,v 1.11 2002/04/23 14:22:13 jstrachan Exp $
 */

package org.dom4j;

import java.util.Iterator;
import java.util.List;

import junit.framework.*;
import junit.textui.TestRunner;

/** A test harness to test the content API in DOM4J
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.11 $
  */
public class TestContent extends AbstractTestCase {

    protected DocumentFactory factory = new DocumentFactory();
    
    public static void main( String[] args ) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite( TestContent.class );
    }
    
    public TestContent(String name) {
        super(name);
    }

    // Test case(s)
    //-------------------------------------------------------------------------                    
    public void testRoot() throws Exception {
        Element root = document.getRootElement();
        assertTrue( "Has root element", root != null );
        
        List authors = root.elements( "author" );
        assertTrue( "Root has children", authors != null && authors.size() == 2 );
        
        Element author1 = (Element) authors.get(0);
        Element author2 = (Element) authors.get(1);
        
        assertTrue( "Author1 is James", author1.attributeValue( "name" ).equals( "James" ) );
        assertTrue( "Author2 is Bob", author2.attributeValue( "name" ).equals( "Bob" ) );
        
        testGetAttributes(author1);
        testGetAttributes(author2);
    }
        
    public void testContent() throws Exception {
        Element root = document.getRootElement();
        assertTrue( "Has root element", root != null );
        
        List content = root.content();
        assertTrue( "Root has content", content != null && content.size() >= 2 );

        boolean iterated = false;
        for ( Iterator iter = content.iterator(); iter.hasNext(); ) {
            Object object = iter.next();
            assertTrue( "Content object is a node", object instanceof Node );
            iterated = true;
        }
        
        assertTrue( "Iteration completed", iterated );
    }
    
    public void testGetNode() throws Exception {
        Element root = document.getRootElement();
        assertTrue( "Has root element", root != null );
        
        int count = root.nodeCount();
        assertTrue( "Root has correct node count", count == 2 );
        
        boolean iterated = false;
        for ( int i = 0; i < count; i++ ) {
            Node node = root.node(i);
            assertTrue( "Valid node returned from node()", node != null );
            iterated = true;
        }
        
        assertTrue( "Iteration completed", iterated );
    }
        
    public void testGetXPathNode() throws Exception {
        Element root = document.getRootElement();
        assertTrue( "Has root element", root != null );
        
        int count = root.nodeCount();
        assertTrue( "Root has correct node count", count == 2 );
        
        boolean iterated = false;
        for ( int i = 0; i < count; i++ ) {
            Node node = root.getXPathResult(i);
            assertTrue( "Valid node returned from node()", node != null );
            assertTrue( "Node supports the parent relationship", node.supportsParent() );
            iterated = true;
        }
        
        assertTrue( "Iteration completed", iterated );
    }
        
    public void testOrderOfPI() throws Exception {
        Document document = factory.createDocument();        
        document.addProcessingInstruction( "xml-stylesheet", "type=\"text/xsl\" href=\"...\"" );
        document.addElement( "root" );
        
        List list = document.content();
        Object pi = list.get(0);
        Object root = list.get(1);
        
        assertTrue( "First element is a PI", pi instanceof ProcessingInstruction );
        assertTrue( "Second element is an element", root instanceof Element );
        
        document = DocumentHelper.parseText(
            "<?xml version=\"1.0\" ?>\n"
            + "<?xml-stylesheet type=\"text/xsl\" href=\"foo\" ?>\n"
            + "<root/>" 
        );
        
        list = document.content();
        pi = list.get(0);
        root = list.get(1);
        
        assertTrue( "First element is a PI", pi instanceof ProcessingInstruction );
        assertTrue( "Second element is an element", root instanceof Element );
        
    }
        
    // Implementation methods
    //-------------------------------------------------------------------------                    
    protected void testGetAttributes(Element author) throws Exception {
        
        String definedName = "name";
        String undefinedName = "undefined-attribute-name";
        String defaultValue = "** Default Value **";
        
        String value = author.attributeValue( definedName, defaultValue );
        assertTrue( "Defined value doesn't return specified default value", value != defaultValue );
        
        value = author.attributeValue( undefinedName, defaultValue );        
        assertTrue( "Undefined value returns specified default value", value == defaultValue );
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
 * $Id: TestContent.java,v 1.11 2002/04/23 14:22:13 jstrachan Exp $
 */
