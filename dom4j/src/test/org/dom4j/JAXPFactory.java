/*
 * Copyright 2001-2004 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: JAXPFactory.java,v 1.2 2004/06/25 08:03:47 maartenc Exp $
 */

package org.dom4j;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.dom4j.io.aelfred2.SAXDriver;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderAdapter;

/*
 * JAXPFactory.java
 *
 * Created on 16 april 2004, 15:14
 */

/**
 *
 * @author  Maarten Coene
 */
public class JAXPFactory extends SAXParserFactory {
    
    private Map features = new HashMap();

    /**
     * Constructs a factory which normally returns a non-validating
     * parser.
     */
    public JAXPFactory () { 
    }

    public SAXParser newSAXParser () throws ParserConfigurationException, SAXException {
        SAXParser result = new InternalParser();
	XMLReader parser = result.getXMLReader ();

	parser.setFeature(
                "http://xml.org/sax/features/namespaces",
                isNamespaceAware());
	parser.setFeature(
                "http://xml.org/sax/features/validation",
                isValidating());

        Iterator it = features.entrySet().iterator();
	while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String name = (String) entry.getKey();
            Boolean value = (Boolean) entry.getValue();
	    parser.setFeature (name, value.booleanValue ());
	}

	return result;
    }

    public void setFeature (String name, boolean value) {
	features.put(name, new Boolean(value));
    }

    public boolean getFeature(String name) {
	Boolean	value = (Boolean) features.get (name);
	
	if (value != null)
	    return value.booleanValue ();
	else {
            return false;
        }
    }

    private static class InternalParser extends SAXParser {
        
	private SAXDriver aelfred;
	private XMLReaderAdapter parser;

	InternalParser() { 
            super();
            
            aelfred = new SAXDriver();
            parser = new XMLReaderAdapter(aelfred);
        }

	public void setProperty(String id, Object value) throws SAXNotRecognizedException, 
                                                                SAXNotSupportedException {
            aelfred.setProperty(id, value); 
        }

	public Object getProperty(String id) throws SAXNotRecognizedException, 
                                                    SAXNotSupportedException {
            return aelfred.getProperty(id); 
        }

	public Parser getParser() { 
	    return parser;
	}

	public XMLReader getXMLReader() {
            return aelfred; 
        }

	public boolean isNamespaceAware() {
            try {
                return aelfred.getFeature("http://xml.org/sax/features/namespaces");
            } catch (Exception e) {
                return false;
            }
	}

	public boolean isValidating()	{
	    try {
		return aelfred.getFeature("http://xml.org/sax/features/validation");
	    } catch (Exception e) {
		return false;
	    }
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
 * $Id: JAXPFactory.java,v 1.2 2004/06/25 08:03:47 maartenc Exp $
 */
