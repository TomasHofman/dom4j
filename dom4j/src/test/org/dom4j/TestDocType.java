/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestDocType.java,v 1.8 2004/04/16 12:48:53 maartenc Exp $
 */

package org.dom4j;

import java.net.URL;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.dom4j.dtd.ElementDecl;
import org.dom4j.io.SAXReader;

/** Tests the DocType functionality
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 1.8 $
  */
public class TestDocType extends AbstractTestCase {

    /** Input XML file to read */
    protected static String INPUT_XML_FILE = "/xml/dtd/internal.xml";
    
    public static void main( String[] args ) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite( TestDocType.class );
    }
    
    public TestDocType(String name) {
        super(name);
    }

    // Test case(s)
    //-------------------------------------------------------------------------                    
    public void testDocType() throws Exception {
        DocumentType docType = document.getDocType();
        assertTrue("Has DOCTYPE", docType!= null);
        
        List declarations = docType.getInternalDeclarations();
        assertTrue("DOCTYPE has declarations", declarations != null && !declarations.isEmpty());
        
        ElementDecl decl = (ElementDecl) declarations.get(0);
        
        assertEquals("name is correct", "greeting", decl.getName() );
        assertEquals("model is correct", "(#PCDATA)", normalize(decl.getModel()));
        
        String expected = "<!ELEMENT " + decl.getName() + " " + decl.getModel() + ">";
        assertEquals("toString() is correct", expected, decl.toString());
    }
    
    /**
     * Removes the optional * at the end of (#PCDATA)
     */
    private String normalize(String model) {
        if ("(#PCDATA)*".equals(model)) {
            return ("(#PCDATA)");
        }
        return model;
    }
        
    // Implementation methods
    //-------------------------------------------------------------------------                    
    protected void setUp() throws Exception {
        SAXReader reader = new SAXReader("org.dom4j.io.aelfred2.SAXDriver");
        reader.setIncludeInternalDTDDeclarations(true);
        
        URL url = getClass().getResource(INPUT_XML_FILE);
        document = reader.read(url);
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
 * $Id: TestDocType.java,v 1.8 2004/04/16 12:48:53 maartenc Exp $
 */
