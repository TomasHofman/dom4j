/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: SchemaBuilder.java,v 1.1 2001/05/24 00:46:17 jstrachan Exp $
 */

package org.dom4j.schema;

import com.sun.tranquilo.datatype.DataType;
import com.sun.tranquilo.datatype.DataTypeFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.QName;

/** <p><code>SchemaBuilder</code> reads an XML Schema Document.</p>
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 1.1 $
  */
public class SchemaBuilder {
    
    // XXXX: might want to use QName instances for these literals...
    private static final String XSD_ELEMENT = "element";
    private static final String XSD_ATTRIBUTE = "attribute";
    private static final String XSD_SIMPLETYPE = "simpleType";
    private static final String XSD_COMPLEXTYPE = "complexType";
    
    
    
    /** Document factory used to register Element specific factories*/
    private SchemaDocumentFactory documentFactory;
    
    /** Cache of <code>DataType</code> instances loaded or created during this build */
    private Map dataTypeCache = new HashMap();
    
    
    public SchemaBuilder() {
        this.documentFactory = SchemaDocumentFactory.singleton;
    }
    
    public SchemaBuilder(SchemaDocumentFactory documentFactory) {
        this.documentFactory = documentFactory;
    }
    
    
    /** Parses the given schema document 
      *
      * @param schemaDocument is the document of the XML Schema
      */
    public void build( Document schemaDocument ) {
        Element root = schemaDocument.getRootElement();
        if ( root != null ) {
            Iterator iter = root.elementIterator( XSD_ELEMENT );
            while ( iter.hasNext() ) {
                onSchemaElement( (Element) iter.next() );
            }
        }
    }
    
    
    // Implementation methods
    //-------------------------------------------------------------------------
    
    /** processes an XML Schema &lt;element&gt; tag 
      */
    protected void onSchemaElement( Element schemaElement ) {
        String name = schemaElement.attributeValue( "name" );
        String type = schemaElement.attributeValue( "type" );
        QName qname = getQName( name );
        
        if ( type != null ) {
            // register type with this element name
        }
        Element schemaComplexType = schemaElement.element( XSD_COMPLEXTYPE );
        if ( schemaComplexType != null ) {
            onSchemaComplexType( qname, schemaComplexType );
        }
        Iterator iter = schemaElement.elementIterator( XSD_ATTRIBUTE );
        if ( iter.hasNext() ) {
            SchemaElementFactory elementFactory 
                = getSchemaElementFactory( qname );

            do {
                onSchemaAttribute( 
                    schemaElement, 
                    elementFactory,
                    (Element) iter.next() 
                );
            }
            while ( iter.hasNext() );
        }
    }
    
    /** processes an XML Schema &lt;complexTypegt; tag 
      */
    protected void onSchemaComplexType( QName elementQName, Element schemaComplexType ) {
    }
    
    /** processes an XML Schema &lt;attribute&gt; tag 
      */
    protected void onSchemaAttribute( 
        Element schemaElement, 
        SchemaElementFactory elementFactory, 
        Element schemaAttribute 
    ) {
        String name = schemaAttribute.attributeValue( "name" );
        String type = schemaAttribute.attributeValue( "type" );
        QName qname = getQName( name );
        DataType dataType = null;
        if ( type != null ) {
            dataType = (DataType) dataTypeCache.get( type );
            if ( dataType == null ) {
                dataType = DataTypeFactory.getTypeByName( type );
                // store in cache for later
                dataTypeCache.put( type, dataType );
            }
        }
        else {
            // must parse the <simpleType> element
            Element simpleTypeElement = schemaAttribute.element( XSD_SIMPLETYPE );
            if ( simpleTypeElement == null ) {
                throw new InvalidSchemaException( 
                    "The attribute: " + name + " in element: " 
                    + schemaElement.getQualifiedName() 
                    + " has no type attribute and does not contain a <simpleType/> element" 
                );
            }
            dataType = loadDataTypeFromSimpleType( simpleTypeElement );            
        }
        if ( dataType != null ) {
            // register the DataType for the given Attribute 
            elementFactory.setAttributeDataType( qname, dataType );
        }
        else {
            System.out.println( "Warning: Couldn't find DataType for type: " + type + " attribute: " + qname.getQualifiedName() );
        }
    }
    
    /** Loads a DataType object from a <simpleType> attribute schema element */
    protected DataType loadDataTypeFromSimpleType( Element simpleTypeElement ) {
        return null;
    }

    /** @return the <code>SchemaElementFactory</code> for the given
      * element QName, creating one if it does not already exist
      */
    protected SchemaElementFactory getSchemaElementFactory( QName elementQName ) {
        SchemaElementFactory factory = documentFactory.getElementFactory( elementQName );               
        if ( factory == null ) {
            factory = new SchemaElementFactory( elementQName );
        }
        return factory;
    }
    
    protected QName getQName( String name ) {
        return documentFactory.createQName(name);
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
 * $Id: SchemaBuilder.java,v 1.1 2001/05/24 00:46:17 jstrachan Exp $
 */
