/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestClone.java,v 1.1 2001/05/30 10:06:43 jstrachan Exp $
 */

package org.dom4j;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import junit.framework.*;
import junit.textui.TestRunner;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.NodeComparator;

/** A test harness to test the clone() methods on Nodes
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.1 $
  */
public class TestClone extends AbstractTestCase {

    private static final boolean VERBOSE = false;
    
    private Comparator comparator = new NodeComparator();
    
    
    public static void main( String[] args ) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite( TestClone.class );
    }
    
    public TestClone(String name) {
        super(name);
    }

    // Test case(s)
    //-------------------------------------------------------------------------                    
    public void testDocumentClone() throws Exception {
        document.setName( "doc1" );
        
        Document doc2 = (Document) document.clone();
        
        assert( "Returned a new document", document != doc2 );
        
        if ( VERBOSE ) {        
            XMLWriter writer = new XMLWriter( 
                System.out, OutputFormat.createPrettyPrint() 
            );
        
            log( "document1" );
            writer.write( document );

            log( "document2" );
            writer.write( doc2 );
        }
        
        assert( "Documents are equal", comparator.compare( document, doc2 ) == 0 );
    }
    
    public void testRootCompare1() throws Exception {                
        Document doc2 = (Document) document.clone();
        Element author = doc2.getRootElement();
        author.setAttributeValue( "foo", "bar" );
        
        assert( "Documents are not equal", comparator.compare( document, doc2 ) != 0 );
    }
    
    public void testRootCompare2() throws Exception {                
        Document doc2 = (Document) document.clone();
        Element author = doc2.getRootElement();
        
        author.addText( "foo" );
        
        assert( "Documents are not equal", comparator.compare( document, doc2 ) != 0 );
    }
    
    public void testAuthorCompare1() throws Exception {                
        Document doc2 = (Document) document.clone();
        Element author = (Element) doc2.selectSingleNode( "//author" );
        author.setAttributeValue( "name", "James Strachan" );
        
        assert( "Documents are not equal", comparator.compare( document, doc2 ) != 0 );
    }
    
    public void testAuthorCompare2() throws Exception {                
        Document doc2 = (Document) document.clone();
        Element author = (Element) doc2.selectSingleNode( "//author" );
        
        author.addText( "foo" );
        
        assert( "Documents are not equal", comparator.compare( document, doc2 ) != 0 );
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
 * $Id: TestClone.java,v 1.1 2001/05/30 10:06:43 jstrachan Exp $
 */
