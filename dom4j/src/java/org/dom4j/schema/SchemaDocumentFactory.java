/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: SchemaDocumentFactory.java,v 1.2 2001/05/24 00:46:17 jstrachan Exp $
 */

package org.dom4j.schema;

import com.sun.tranquilo.datatype.DataType;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

import org.xml.sax.Attributes;

/** <p><code>SchemaDocumentFactory</code> is a factory of XML objects which 
  * support the 
  * <a href="http://www.w3.org/TR/xmlschema-2/">XML Schema Data Types</a>
  * specification.</p>
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 1.2 $
  */
public class SchemaDocumentFactory extends DocumentFactory {

    // I don't think interning of QNames is necessary
    private static final boolean DO_INTERN_QNAME = false;
    
    
    /** The Singleton instance */
    static SchemaDocumentFactory singleton = new SchemaDocumentFactory();
    
    private static final Namespace XSI_NAMESPACE
        = Namespace.get( "xsi", "http://www.w3.org/2001/XMLSchema-instance" );
    
    private static final QName XSI_NO_SCHEMA_LOCATION
        = QName.get( "noNamespaceSchemaLocation", XSI_NAMESPACE );
    

    /** The builder of XML Schemas */
    private SchemaBuilder schemaBuilder;
    
    /** reader of XML Schemas */
    private SAXReader xmlSchemaReader = new SAXReader();

    
    /** <p>Access to the singleton instance of this factory.</p>
      *
      * @return the default singleon instance
      */
    public static DocumentFactory getInstance() {
        return singleton;
    }
    
    public SchemaDocumentFactory() {
        schemaBuilder = new SchemaBuilder(this);
    }
    
    
    /** Registers the given <code>SchemaElementFactory</code> for the given 
      * &lt;element&gt; schema element
      */
    public SchemaElementFactory getElementFactory( QName elementQName ) {
        if ( DO_INTERN_QNAME ) {
            elementQName = intern( elementQName );
        }
        DocumentFactory factory = elementQName.getDocumentFactory();
        return (factory instanceof SchemaElementFactory) 
            ? (SchemaElementFactory) factory : null;
    }
    
        
    // DocumentFactory methods
    //-------------------------------------------------------------------------
    
    public Element createElement(QName qname, Attributes attributes) {
        //System.out.println( "Creating element for: " + qname );
        //System.out.println( "Has factory: " + qname.getDocumentFactory() );
        return super.createElement(qname, attributes);
    }
    
    public Attribute createAttribute(QName qname, String value) {
        if ( qname.equals( XSI_NO_SCHEMA_LOCATION ) ) {
            loadSchema( value );
        }
        return super.createAttribute( qname, value );
    }
    

    
    // Implementation methods
    //-------------------------------------------------------------------------
    protected void loadSchema( String schemaInstanceURI ) {
        try {
            // XXXX: massive hack!            
            schemaInstanceURI = "xml/schema/" + schemaInstanceURI;
            Document schemaDocument = xmlSchemaReader.read( schemaInstanceURI );
            schemaBuilder.build( schemaDocument );
        }
        catch (Exception e) {
            System.out.println( "Failed to load schema: " + schemaInstanceURI );
            System.out.println( "Caught: " + e );
            e.printStackTrace();
            throw new InvalidSchemaException( "Failed to load schema: " + schemaInstanceURI );
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
 * $Id: SchemaDocumentFactory.java,v 1.2 2001/05/24 00:46:17 jstrachan Exp $
 */
