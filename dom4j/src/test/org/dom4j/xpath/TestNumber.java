/*
 * Copyright 2001-2004 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestNumber.java,v 1.13 2004/06/25 08:03:51 maartenc Exp $
 */

package org.dom4j.xpath;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.dom4j.AbstractTestCase;
import org.dom4j.Node;
import org.dom4j.XPath;

/** Test harness for numeric XPath expressions
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.13 $
  */
public class TestNumber extends AbstractTestCase {

    protected static boolean VERBOSE = false;
    
    protected static String[] paths = {
        "2+2",
        "2 + 2",
        "2 + number(1) + 2",
        "number(1) * 2",
        "2 + count(//author) + 2",
        "2 + (2 * 5)",
        "count(//author) + count(//author/attribute::*)",
        "(12 + count(//author) + count(//author/attribute::*)) div 2",
        "count(//author)",
        "count(//author/attribute::*)",
        "2 + number(1) * 2",
        "count(descendant::author)",
        "count(ancestor::author)",
        "count(descendant::*)",
        "count(descendant::author)+1",
        "count(ancestor::*)",
//        "10 + count(ancestor-or-self::author) + 5",
        "10 + count(descendant::author) * 5",
        "10 + (count(descendant::author) * 5)",
    };
    
    
    public static void main( String[] args ) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite( TestNumber.class );
    }
    
    public TestNumber(String name) {
        super(name);
    }

    // Test case(s)
    //-------------------------------------------------------------------------                    
    public void testXPaths() throws Exception {        
        Node element = document.selectSingleNode( "//author" );
        int size = paths.length;
        for ( int i = 0; i < size; i++ ) {
            testXPath( document, paths[i] );
            testXPath( element, paths[i] );
        }
        log( "Finished successfully" );
    }
        
    // Implementation methods
    //-------------------------------------------------------------------------                    
    protected void testXPath(Node node, String xpathText) throws Exception {
        try {
            XPath xpath = node.createXPath( xpathText );
            
            if ( VERBOSE ) {
                log( "    xpath: " + xpath );        
                log( "    for: " + node );        
            }
            
            Number number = xpath.numberValueOf( node );

            log( "Searched path: " + xpathText + " found: " + number );

        }
        catch (Throwable e) {
            log( "Caught exception: " + e );
            e.printStackTrace();
            assertTrue( "Failed to process:  " + xpathText + " caught exception: " + e, false );
        }
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
 * 5. Due credit should be given to the DOM4J Project - 
 *    http://www.dom4j.org
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
 * Copyright 2001-2004 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * $Id: TestNumber.java,v 1.13 2004/06/25 08:03:51 maartenc Exp $
 */
