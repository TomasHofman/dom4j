/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestXPath.java,v 1.4 2001/01/10 19:00:14 jstrachan Exp $
 */

package org.dom4j;

import java.util.Iterator;
import java.util.List;

import junit.framework.*;
import junit.textui.TestRunner;

/** A test harness to test XPath expression evaluation in DOM4J
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.4 $
  */
public class TestXPath extends AbstractTestCase {

    protected static boolean VERBOSE = true;
    
    protected static String[] paths = {
        "root",
        "text()",
        "//author",
        "//author[@location='UK']",
        "//author/text()",
        "//*[.='James Strachan']",
        "//@location"
    };
    
    
    public TestXPath(String name) {
        super(name);
    }

    // Test case(s)
    //-------------------------------------------------------------------------                    
    public void testXPaths() throws Exception {        
        int size = paths.length;
        for ( int i = 0; i < size; i++ ) {
            testXPath( paths[i] );
        }
    }
        
    // JUnit stuff
    //-------------------------------------------------------------------------                    
    public static void main( String[] args ) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite( TestXPath.class );
    }
    
    protected void testXPath(String xpath) {
        List list = document.selectNodes(xpath);
        
        System.out.println( "Searched path: " + xpath + " found: " + list.size() + " result(s)" );
        
        if ( VERBOSE ) {
            System.out.println( list );
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
 * $Id: TestXPath.java,v 1.4 2001/01/10 19:00:14 jstrachan Exp $
 */
