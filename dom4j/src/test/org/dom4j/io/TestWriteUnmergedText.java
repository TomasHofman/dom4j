/*
 * Copyright 2001-2004 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestWriteUnmergedText.java,v 1.2 2004/06/25 08:03:50 maartenc Exp $
 */

package org.dom4j.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import junit.framework.*;
import junit.textui.TestRunner;

import org.dom4j.*;

import java.io.StringWriter;
import java.io.StringReader;

/** A simple test harness to check that the XML Writer works
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.2 $
  */
public class TestWriteUnmergedText extends AbstractTestCase {


    private String inputText = "<?xml version = \"1.0\"?><TestEscapedEntities><TEXT>Test using &lt; &amp; &gt;</TEXT></TestEscapedEntities>";
    protected static final boolean VERBOSE = true;
    
    
    public static void main( String[] args ) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite( TestWriteUnmergedText.class );
    }
    
    public TestWriteUnmergedText(String name) {
        super(name);
    }

    // Test case(s)
    //-------------------------------------------------------------------------                    
    public String readwriteText(OutputFormat outFormat, boolean mergeAdjacentText) throws Exception {
        
        StringWriter out = new StringWriter();
        StringReader in = new StringReader(inputText);
        SAXReader reader = new SAXReader();
 
        //reader.setValidation(true);
        reader.setMergeAdjacentText(mergeAdjacentText);

        Document document = reader.read(in);

        XMLWriter writer = outFormat == null ? new XMLWriter(out) : new XMLWriter(out, outFormat);
        writer.write(document);
        writer.close(); 
        
        String outText = out.toString();
        return outText;
    }        
    
    
    public void testWithoutFormatNonMerged() throws Exception {

        String outText = readwriteText(null, false);
            
        if ( VERBOSE ) {
            log( "Text output is ["  );
            log( outText );
            log( "]. Done" );
        }
        
        // should contain &amp; and &lt;
        assertTrue("Output text contains \"&amp;\"", outText.lastIndexOf("&amp;") >= 0);
        assertTrue("Output text contains \"&lt;\"", outText.lastIndexOf("&lt;") >= 0);
    } 
           
    public void testWithCompactFormatNonMerged() throws Exception {

        String outText = readwriteText(OutputFormat.createCompactFormat(), false);
            
        if ( VERBOSE ) {
            log( "Text output is ["  );
            log( outText );
            log( "]. Done" );
        }
        
        // should contain &amp; and &lt;
        assertTrue("Output text contains \"&amp;\"", outText.lastIndexOf("&amp;") >= 0);
        assertTrue("Output text contains \"&lt;\"", outText.lastIndexOf("&lt;") >= 0);
    }
            
    public void testWithPrettyPrintFormatNonMerged() throws Exception {

        String outText = readwriteText(OutputFormat.createPrettyPrint(), false);
            
        if ( VERBOSE ) {
            log( "Text output is ["  );
            log( outText );
            log( "]. Done" );
        }
        
        // should contain &amp; and &lt;
        assertTrue("Output text contains \"&amp;\"", outText.lastIndexOf("&amp;") >= 0);
        assertTrue("Output text contains \"&lt;\"", outText.lastIndexOf("&lt;") >= 0);
    }        
   
    public void testWithoutFormatMerged() throws Exception {

        String outText = readwriteText(null, true);
            
        if ( VERBOSE ) {
            log( "Text output is ["  );
            log( outText );
            log( "]. Done" );
        }
        
        // should contain &amp; and &lt;
        assertTrue("Output text contains \"&amp;\"", outText.lastIndexOf("&amp;") >= 0);
        assertTrue("Output text contains \"&lt;\"", outText.lastIndexOf("&lt;") >= 0);
    } 
           
    public void testWithCompactFormatMerged() throws Exception {

        String outText = readwriteText(OutputFormat.createCompactFormat(), true);
            
        if ( VERBOSE ) {
            log( "Text output is ["  );
            log( outText );
            log( "]. Done" );
        }
        
        // should contain &amp; and &lt;
        assertTrue("Output text contains \"&amp;\"", outText.lastIndexOf("&amp;") >= 0);
        assertTrue("Output text contains \"&lt;\"", outText.lastIndexOf("&lt;") >= 0);
    }
            
    public void testWithPrettyPrintFormatMerged() throws Exception {

        String outText = readwriteText(OutputFormat.createPrettyPrint(), true);
            
        if ( VERBOSE ) {
            log( "Text output is ["  );
            log( outText );
            log( "]. Done" );
        }
        
        // should contain &amp; and &lt;
        assertTrue("Output text contains \"&amp;\"", outText.lastIndexOf("&amp;") >= 0);
        assertTrue("Output text contains \"&lt;\"", outText.lastIndexOf("&lt;") >= 0);
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
 * $Id: TestWriteUnmergedText.java,v 1.2 2004/06/25 08:03:50 maartenc Exp $
 */
