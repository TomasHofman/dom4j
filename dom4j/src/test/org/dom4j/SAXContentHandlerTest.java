/*
 * Copyright 2001-2004 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: SAXContentHandlerTest.java,v 1.1 2004/11/12 21:33:18 maartenc Exp $
 */

package org.dom4j;

import java.io.File;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.textui.TestRunner;

import org.dom4j.io.SAXContentHandler;
import org.dom4j.io.SAXReader;
import org.xml.sax.XMLReader;

public class SAXContentHandlerTest extends AbstractTestCase {
    private XMLReader xmlReader;

    protected String[] testDocuments = {
        "/xml/test/test_schema.xml",
        //"xml/test/encode.xml",
        "/xml/fibo.xml",
        "/xml/test/schema/personal-prefix.xsd",
        //"xml/test/soap2.xml",
    };

	public static void main(String[] args) {
		TestRunner.run(SAXContentHandlerTest.class);
	}

    protected void setUp() throws Exception {
    	super.setUp();
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser parser = spf.newSAXParser();
        xmlReader = parser.getXMLReader();
    }

    public void testSAXContentHandler() throws Exception {

        SAXContentHandler contentHandler = new SAXContentHandler();
        xmlReader.setContentHandler(contentHandler);
        xmlReader.setDTDHandler(contentHandler);
        xmlReader.setProperty("http://xml.org/sax/properties/lexical-handler", contentHandler);

        for ( int i = 0, size = testDocuments.length; i < size; i++ ) {
            SAXReader reader = new SAXReader();
            File file = new File(testDocuments[i]);
            URL url = getClass().getResource(testDocuments[i]);
            Document docFromSAXReader = reader.read(url);

            xmlReader.parse(url.toString());
            Document docFromSAXContentHandler = contentHandler.getDocument();

            //System.out.println("docFromSAXReader = " + docFromSAXReader.asXML());
            //System.out.println("docFromSAXContentHandler = " + docFromSAXContentHandler.asXML());

            docFromSAXContentHandler.setName(docFromSAXReader.getName());

            assertDocumentsEqual(docFromSAXReader, docFromSAXContentHandler);
            assertEquals(docFromSAXReader.asXML(), docFromSAXContentHandler.asXML());
        }
    }
    
    public void testBug926713() throws Exception {
        URL url = getClass().getResource("/xml/test/cdata.xml");
        SAXReader reader = new SAXReader();
        
        Document doc = reader.read(url);
        Element foo = doc.getRootElement();
        Element bar = foo.element("bar");
        List content = bar.content();
        assertEquals(3, content.size());
        assertEquals(Node.TEXT_NODE, ((Node) content.get(0)).getNodeType());
        assertEquals(Node.CDATA_SECTION_NODE, ((Node) content.get(1)).getNodeType());
        assertEquals(Node.TEXT_NODE, ((Node) content.get(2)).getNodeType());
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
 * $Id: TestXSLT.java,v
 */