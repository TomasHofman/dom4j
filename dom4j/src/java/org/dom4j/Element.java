/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: Element.java,v 1.23 2001/03/01 23:07:46 jstrachan Exp $
 */

package org.dom4j;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** <p><code>Element</code> interface defines an XML element.
  * An element can have declared namespaces, attributes, child nodes and 
  * textual content.</p>
  *
  * <p>Some of this interface is optional. 
  * Some implementations may be read-only and not support being modified.
  * Some implementations may not support the parent relationship and methods
  * such as {@link #getParent} or {@link #getDocument}.</p>
  *
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.23 $
  */
public interface Element extends Branch {

 
    /** @return true if this element is the root element of a document
      * and this element supports the parent relationship else false.
      */
    public boolean isRootElement();

    /** <p>Returns the <code>QName</code> of this element which represents 
      * the local name, the qualified name and the <code>Namespace</code>.</p>
      *
      * @return the <code>QName</code> associated with this element
      */
    public QName getQName();    
    
    /** <p>Returns the <code>Namespace</code> of this element if one exists 
      * otherwise <code>Namespace.NO_NAMESPACE</code> is returned.</p>
      *
      * @return the <code>Namespace</code> associated with this element
      */
    public Namespace getNamespace();

    
 
    /** <p>Returns the <code>Namespace</code> which is mapped to the given
      * prefix or null if it could not be found.</p>
      *
      * @return the <code>Namespace</code> associated with the given prefix
      */
    public Namespace getNamespaceForPrefix(String prefix);

    /** <p>Returns the <code>Namespace</code> which is mapped to the given
      * URI or null if it could not be found.</p>
      *
      * @return the <code>Namespace</code> associated with the given URI
      */
    public Namespace getNamespaceForURI(String uri);
    
   /** <p>Returns any additional namespaces declarations for this element 
      * other than namespace returned via the {@link #getNamespace()} method. 
      * If no additional namespace declarations are present for this
      * element then an empty list will be returned.
      * 
      * The list is backed by the element such that changes to the list will
      * be reflected in the element though the reverse is not the case.</p>
      *
      * @return a list of any additional namespace declarations.
      */
    public List getAdditionalNamespaces();
    
   /** <p>Returns all the namespaces declared by this element. 
      * If no namespaces are declared for this element then 
      * an empty list will be returned.
      * 
      * The list is backed by the element such that changes to the list will
      * be reflected in the element though the reverse is not the case.</p>
      *
      * @return a list of namespaces declared for this element.
      */
    public List getDeclaredNamespaces();
    
    /** <p>Returns the namespace prefix of this element if one exists 
      * otherwise an empty <code>String</code> is returned.</p>
      *
      * @return the prefix of the <code>Namespace</code> of this element 
      * or an empty <code>String</code>
      */
    public String getNamespacePrefix();

    /** <p>Returns the URI mapped to the namespace of this element 
      * if one exists otherwise an empty <code>String</code> is returned.</p>
      *
      * @return the URI for the <code>Namespace</code> of this element 
      * or an empty <code>String</code>
      */
    public String getNamespaceURI();

    /** <p>Returns the fully qualified name of this element. 
      * This will be the same as the value returned from {@link #getName}
      * if this element has no namespace attached to this element or an
      * expression of the form
      * <pre>
      * getNamespacePrefix() + ":" + getName()
      * </pre>
      * will be returned.
      *
      * @return the fully qualified name of the element.
      */
    public String getQualifiedName();
    
    
    // Text API
    
    /** Returns the text value of this element without recursing through
      * child elements. 
      * This method iterates through all {@link Text}, {@link CDATA} and 
      * {@link Entity} nodes that this element contains 
      * and appends the text values together.
      * 
      * @return the textual content of this Element. Child elements are not navigated.
      */
    public String getText();    
   
    /** @return the trimmed text value where whitespace is trimmed and
      * normalised into single spaces
      */
    public String getTextTrim();

    
    /** Returns the XPath string-value of this node. 
      * The behaviour of this method is defined in the 
      * <a href="http://www.w3.org/TR/xpath">XPath specification</a>.
      *
      * This method returns the string-value of all the contained 
      * {@link Text}, {@link CDATA}, {@link Entity} and {@link Element} nodes 
      * all appended together.
      * 
      * @return the text from all the child Text and Element nodes appended 
      * together.
      */
    public String getString();    


    /** Accesses the data of this element which may implement data typing 
      * bindings such as XML Schema or 
      * Java Bean bindings or will return the same value as {@link #getText}
      */
    public Object getData();
    
    /** Sets the data value of this element if this element supports data 
      * binding or calls {@link #setText} if it doesn't
      */
    public void setData(Object data);
    
    // Attributes API
    

    /** <p>Returns the {@link Attribute} instances this element contains as 
      * a backed {@link List} so that the attributes may be modified directly 
      * using the {@link List} interface.
      * The <code>List</code> is backed by the <code>Element</code> so that
      * changes to the list are reflected in the element and vice versa.</p>
      *
      * @return the attributes that this element contains as a <code>List</code>
      */    
    public List getAttributes();
    
    /** Sets the attributes that this element contains
      */
    public void setAttributes(List attributes);

    /** @return the number of attributes this element contains
      */
    public int getAttributeCount();
    
    /** @returns an iterator over the attributes of this element
      */
    public Iterator attributeIterator();
    
    /** Returns the attribute at the specified indexGets the 
      *
      * @return the attribute at the specified index where 
      * index >= 0 and index < number of attributes or throws
      * an IndexOutOfBoundsException if the index is not within the 
      * allowable range
      */
    public Attribute getAttribute(int index);
            
    /** Returns the attribute with the given name
      *
      * @return the attribute for the given local name in any namespace.
      * If there are more than one attributes with the given local name 
      * in different namespaces then the first one is returned.
      */
    public Attribute getAttribute(String name);
    
    /** @param qName is the fully qualified name
      * @return the attribute for the given fully qualified name or null if 
      * it could not be found.
      */
    public Attribute getAttribute(QName qname);

    /** <p>This returns the attribute value for the attribute with the 
      * given name and any namespace or null if there is no such 
      * attribute or the empty string if the attribute value is empty.</p>
      *
      * @param name is the name of the attribute value to be returnd
      * @return the value of the attribute, null if the attribute does 
      * not exist or the empty string
      */
    public String getAttributeValue(String name);

    /** <p>This returns the attribute value for the attribute with the 
      * given name and any namespace or the default value if there is 
      * no such attribute value.</p>
      *
      * @param name is the name of the attribute value to be returnd
      * @param defaultValue is the default value to be returned if the 
      *    attribute has no value defined.
      * @return the value of the attribute or the defaultValue if the 
      *    attribute has no value defined.
      */
    public String getAttributeValue(String name, String defaultValue);

    /** <p>This returns the attribute value for the attribute with the 
      * given fully qualified name or null if there is no such 
      * attribute or the empty string if the attribute value is empty.</p>
      *
      * @param qName is the fully qualified name
      * @return the value of the attribute, null if the attribute does 
      * not exist or the empty string
      */
    public String getAttributeValue(QName qName);

    /** <p>This returns the attribute value for the attribute with the 
      * given fully qualified name or the default value if 
      * there is no such attribute value.</p>
      *
      * @param qName is the fully qualified name
      * @param defaultValue is the default value to be returned if the 
      *    attribute has no value defined.
      * @return the value of the attribute or the defaultValue if the 
      *    attribute has no value defined.
      */
    public String getAttributeValue(QName qName, String defaultValue);

    
    /** <p>Sets the attribute value of the given local name.</p>
      *
      * @param name is the name of the attribute whose value is to be added 
      * or updated
      * @param value is the attribute's value
      */
    public void setAttributeValue(String name, String value);
    
    /** <p>Sets the attribute value of the given fully qualified name.</p>
      *
      * @param qName is the fully qualified name of the attribute 
      * whose value is to be added or updated
      * @param value is the attribute's value
      */
    public void setAttributeValue(QName qName, String value);

    /** <p>Removes the first attribute with the given name and any namespace.</p>
      *
      * @param name is the name of the attribute to be removed
      * @return the attribute that was removed or null if none was removed
      */
    public Attribute removeAttribute(String name);
    
    /** <p>Removes the attribute with the given fully qualified name.</p>
      *
      * @param qName is the fully qualified name to search for
      * @return the attribute that was removed or null if none was removed
      */
    public Attribute removeAttribute(QName qName);

    
    
    // Content API
    
    /** Returns a node at the given index suitable for an XPath result set.
      * This means the resulting Node will either be null or it will support 
      * the parent relationship.
      *
      * @return the Node for the given index which will support the parent 
      * relationship or null if there is not a node at the given index.
      */
    public Node getXPathNode(int index);
    
    
    /** <p>Returns true if this <code>Element</code> has mixed content.
      * Mixed content means that an element contains both textual data and
      * child elements.
      *
      * @return true if this element contains mixed content.
      */
    public boolean hasMixedContent();    
    
    // return the child elements
    public Element getElementByID(String elementID);
    
    
    /** Returns the first element for the given local name and any namespace.
      * 
      * @return the first element with the given local name 
      */
    public Element getElement(String name);
    
    /** Returns the first element for the given fully qualified name.
      * 
      * @param qName is the fully qualified name to search for
      * @return the first element with the given fully qualified name
      */
    public Element getElement(QName qname);

    /** <p>Returns the elements contained in this element. 
      * If this element does not contain any elements then this method returns
      * an empty list.
      *
      * The list is backed by the element such that changes to the list will
      * be reflected in the element though the reverse is not the case.</p>
      *
      * @return a list of all the elements in this element.
      */
    public List getElements();
    
    /** <p>Returns the elements contained in this element with the given 
      * local name and any namespace.
      * If no elements are found then this method returns an empty list.
      *
      * The list is backed by the element such that changes to the list will
      * be reflected in the element though the reverse is not the case.</p>
      *
      * @return a list of all the elements in this element for the given 
      * local name
      */
    public List getElements(String name);
    
    /** <p>Returns the elements contained in this element with the given 
      * fully qualified name.
      * If no elements are found then this method returns an empty list.
      *
      * The list is backed by the element such that changes to the list will
      * be reflected in the element though the reverse is not the case.</p>
      *
      * @param qName is the fully qualified name to search for
      * @return a list of all the elements in this element for the 
      * given fully qualified name.
      */
    public List getElements(QName qName);
    
    /** Returns an iterator over all this elements child elements.
      *
      * @return an iterator over the contained elements
      */
    public Iterator elementIterator();
    
    /** Returns an iterator over the elements contained in this element
      * which match the given local name and any namespace.
      *
      * @return an iterator over the contained elements matching the given 
      * local name
      */
    public Iterator elementIterator(String name);
    
    /** Returns an iterator over the elements contained in this element
      * which match the given fully qualified name.
      *
      * @param qName is the fully qualified name to search for
      * @return an iterator over the contained elements matching the given 
      * fully qualified name
      */
    public Iterator elementIterator(QName qname);
        
    
    // helper methods
    public String getElementText(String name);
    public String getElementText(QName qname);
    public String getElementTextTrim(String name);
    public String getElementTextTrim(QName qname);
    

    // builder methods 
    
    /** Adds a new <code>CDATA</code> node with the given text to this element.
      *
      * @param cdata is the text for the <code>CDATA</code> node.
      */    
    public void addCDATA(String cdata);
    
    /** Adds a new <code>Text</code> node with the given text to this element.
      *
      * @param text is the text for the <code>Text</code> node.
      */    
    public void addText(String text);    
    
    /** Adds a new <code>Entity</code> node with the given name to this element
      * and returns a reference to the new node.
      *
      * @param name is the name for the <code>Entity</code> node.
      * @return the newly added <code>Entity</code> node.
      */    
    public Entity addEntity(String name);
    
    /** Adds a new <code>Entity</code> node with the given name and text
      * to this element and returns a reference to the new node.
      *
      * @param name is the name for the <code>Entity</code> node.
      * @param text is the text for the <code>Entity</code> node.
      * @return the newly added <code>Entity</code> node.
      */    
    public Entity addEntity(String name, String text);
    
    public Namespace addNamespace(String prefix, String uri);

    // typesafe versions using node classes
    
    
    /** Adds the given <code>Attribute</code> to this element.
      * If the given node already has a parent defined then an
      * <code>InvalidAddNodeException</code> will be thrown.
      *
      * @param attribute is the attribute to be added
      */
    public void add(Attribute attribute);
    
    
    /** Adds the given <code>CDATA</code> to this element.
      * If the given node already has a parent defined then an
      * <code>InvalidAddNodeException</code> will be thrown.
      *
      * @param cdata is the CDATA to be added
      */
    public void add(CDATA cdata);
    
    /** Adds the given <code>Entity</code> to this element.
      * If the given node already has a parent defined then an
      * <code>InvalidAddNodeException</code> will be thrown.
      *
      * @param entity is the entity to be added
      */
    public void add(Entity entity);
    
    /** Adds the given <code>Text</code> to this element.
      * If the given node already has a parent defined then an
      * <code>InvalidAddNodeException</code> will be thrown.
      *
      * @param text is the text to be added
      */
    public void add(Text text);
    
    /** Adds the given <code>Namespace</code> to this element.
      * If the given node already has a parent defined then an
      * <code>InvalidAddNodeException</code> will be thrown.
      *
      * @param namespace is the namespace to be added
      */
    public void add(Namespace namespace);
        
    /** Removes the given <code>Attribute</code> from this element.
      *
      * @param attribute is the attribute to be removed
      * @return true if the attribute was removed
      */
    public boolean remove(Attribute attribute);
    
    /** Removes the given <code>CDATA</code> from this element.
      *
      * @param cdata is the CDATA to be removed
      * @return true if the cdata was removed
      */
    public boolean remove(CDATA cdata);
    
    /** Removes the given <code>Entity</code> from this element.
      *
      * @param entity is the entity to be removed
      * @return true if the entity was removed
      */
    public boolean remove(Entity entity);
    
    /** Removes the given <code>Namespace</code> from this element.
      *
      * @param namespace is the namespace to be removed
      * @return true if the namespace was removed
      */
    public boolean remove(Namespace namespace);
    
    /** Removes the given <code>Text</code> from this element.
      *
      * @param text is the text to be removed
      * @return true if the text was removed
      */
    public boolean remove(Text text);
    
    
    /** Appends the attributes of the given element to me.
      * This method behaves like the {@link Collection#addAll(java.util.Collection)} 
      * method.
      *
      * @param element is the element whose attributes will be added to me.
      */
    public void appendAttributes(Element element);
    
    /** Appends the content of the given element to me.
      * This method behaves like the {@link Collection#addAll(java.util.Collection)} 
      * method.
      *
      * @param element is the element whose content will be added to me.
      */
    public void appendContent(Element element);
    
    /** Appends the additional namespace declarations of the given element to me.
      * This method behaves like the {@link Collection#addAll(java.util.Collection)} 
      * method.
      *
      * @param element is the element whose additional namespaces will be added to me.
      */
    public void appendAddtionalNamespaces(Element element);

    // creates a copy
    public Element createCopy();
    public Element createCopy(String name);
    public Element createCopy(QName qName);
    
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
 * $Id: Element.java,v 1.23 2001/03/01 23:07:46 jstrachan Exp $
 */
