/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: DefaultElement.java,v 1.27 2001/05/18 09:33:26 jstrachan Exp $
 */

package org.dom4j.tree;

import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.dom4j.Attribute;
import org.dom4j.Branch;
import org.dom4j.CDATA;
import org.dom4j.CharacterData;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.dom4j.IllegalAddException;
import org.dom4j.Node;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.ProcessingInstruction;
import org.dom4j.Text;

import org.xml.sax.Attributes;

/** <p><code>DefaultElement</code> is the default DOM4J default implementation
  * of an XML element.</p>
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.27 $
  */
public class DefaultElement extends AbstractElement {

    protected static final List EMPTY_LIST = Collections.EMPTY_LIST;
    protected static final Iterator EMPTY_ITERATOR = EMPTY_LIST.iterator();
    
    /** The <code>QName</code> for this element */
    private QName qname;
    
    /** Stores the parent branch of this node which is either a Document 
      * if this element is the root element in a document, or another Element 
      * if it is a child of the root document, or null if it has not been added
      * to a document yet. 
       */
    private Branch parentBranch;

    /** Stores null for no content, a Node for a single content node
      * or a List for multiple content nodes. 
      * The List will be lazily constructed when required. */
    private Object content;
    
    /** Lazily constructes list of attributes */
    private List attributes;

    
    
    public DefaultElement(String name) { 
        this.qname = getDocumentFactory().createQName(name);
    }

    public DefaultElement(QName qname) { 
        this.qname = qname;
    }

    public DefaultElement(QName qname, Attributes attributes) { 
        this.qname = qname;
        int size = attributes.getLength();
        if ( size > 0 ) {
            // XXXX: may want to use lazy creation such that 
            // XXXX: no List is created for elements with one attribute
            this.attributes = new ArrayList( size );
        }
    }

    public DefaultElement(String name, Namespace namespace) { 
        this.qname = getDocumentFactory().createQName(name, namespace);
    }

    public Element getParent() {
        return ( parentBranch instanceof Element ) 
            ? (Element) parentBranch : null;
    }

    public void setParent(Element parent) {
        if ( parentBranch instanceof Element || parent != null ) {
            parentBranch = parent;
        }
    }

    public Document getDocument() {
        if ( parentBranch instanceof Document ) {
            return (Document) parentBranch;
        }
        else if ( parentBranch instanceof Element ) {
            Element parent = (Element) parentBranch;
            return parent.getDocument();
        }
        return null;
    }
    
    public void setDocument(Document document) {
        if ( parentBranch instanceof Document || document != null ) {
            parentBranch = document;
        }
    }
    
    public boolean supportsParent() {
        return true;
    }

    public QName getQName() {
        return qname;
    }
    
    
    public String getText() {
        if ( content instanceof List ) {
            return super.getText();
        }
        else {
            if ( content != null ) {
                return getContentAsText( content );
            }
            else {
                return "";
            }
        }
    }
    
    public String getStringValue() {
        if ( content instanceof List ) {
            List list = (List) content;
            int size = list.size();
            if ( size > 0 ) {
                if ( size == 1 ) {
                    // optimised to avoid StringBuffer creation
                    return getContentAsStringValue( list.get(0) );
                }
                else {
                    StringBuffer buffer = new StringBuffer();
                    for ( int i = 0; i < size; i++ ) {
                        Object node = list.get(i);
                        String string = getContentAsStringValue( node ); 
                        if ( string.length() > 0 ) {
                            if ( buffer.length() > 0 ) {
                                buffer.append( ' ' );
                            }
                            buffer.append( string );
                        }
                    }
                    return buffer.toString();
                }
            }
        }
        else {
            if ( content != null ) {
                return getContentAsStringValue( content );
            }
        }
        return "";
    }
    
    
    public Namespace getNamespaceForPrefix(String prefix) {
        if ( prefix == null || prefix.length() <= 0 ) {
            return Namespace.NO_NAMESPACE;
        }
        else if ( prefix.equals( getNamespacePrefix() ) ) {
            return getNamespace();
        }
        else if ( prefix.equals( "xml" ) ) {
            return Namespace.XML_NAMESPACE;
        }
        else {
            if ( content instanceof List ) {
                List list = (List) content;
                int size = list.size();
                for ( int i = 0; i < size; i++ ) {
                    Object object = list.get(i);
                    if ( object instanceof Namespace ) {
                        Namespace namespace = (Namespace) object;
                        if ( prefix.equals( namespace.getPrefix() ) ) {
                            return namespace;
                        }
                    }
                }
            }
            else 
            if ( content instanceof Namespace ) {
                Namespace namespace = (Namespace) content;
                if ( prefix.equals( namespace.getPrefix() ) ) {
                    return namespace;
                }
            }
            Element parent = getParent();
            if ( parent != null ) {
                return parent.getNamespaceForPrefix(prefix);
            }
        }
        return null;
    }
   
    public Namespace getNamespaceForURI(String uri) {
        if ( uri == null || uri.length() <= 0 ) {
            return Namespace.NO_NAMESPACE;
        }
        else if ( uri.equals( getNamespaceURI() ) ) {
            return getNamespace();
        }
        else {
            if ( content instanceof List ) {
                List list = (List) content;
                int size = list.size();
                for ( int i = 0; i < size; i++ ) {
                    Object object = list.get(i);
                    if ( object instanceof Namespace ) {
                        Namespace namespace = (Namespace) object;
                        if ( uri.equals( namespace.getURI() ) ) {
                            return namespace;
                        }
                    }
                }
            }
            else 
            if ( content instanceof Namespace ) {
                Namespace namespace = (Namespace) content;
                if ( uri.equals( namespace.getURI() ) ) {
                    return namespace;
                }
            }
            Element parent = getParent();
            if ( parent != null ) {
                return parent.getNamespaceForURI(uri);
            }
            return null;
        }
    }
    
    public List declaredNamespaces() {
        BackedList answer = createResultList();
        if ( getNamespaceURI().length() > 0 ) {
            answer.addLocal( getNamespace() );
        }
        if ( content instanceof List ) {
            List list = (List) content;
            int size = list.size();
            for ( int i = 0; i < size; i++ ) {
                Object object = list.get(i);
                if ( object instanceof Namespace ) {
                    answer.addLocal( object );
                }
            }
        }
        else {
            if ( content instanceof Namespace ) {
                answer.addLocal( content );
            }
        }
        return answer;
    }
    
    public List additionalNamespaces() {
        if ( content instanceof List ) {
            List list = (List) content;
            int size = list.size();
            BackedList answer = createResultList();
            for ( int i = 0; i < size; i++ ) {
                Object object = list.get(i);
                if ( object instanceof Namespace ) {
                    Namespace namespace = (Namespace) object;
                    answer.addLocal( namespace );
                }
            }
            return answer;
        }
        else {
            if ( content instanceof Namespace ) {
                Namespace namespace = (Namespace) content;
                return createSingleResultList( namespace );
            }
            else {
                return createEmptyList();
            }
        }
    }
    
    public List additionalNamespaces(String defaultNamespaceURI) {
        if ( content instanceof List ) {
            List list = (List) content;
            BackedList answer = createResultList();
            int size = list.size();
            for ( int i = 0; i < size; i++ ) {
                Object object = list.get(i);
                if ( object instanceof Namespace ) {
                    Namespace namespace = (Namespace) object;
                    if ( ! defaultNamespaceURI.equals( namespace.getURI() ) ) {
                        answer.addLocal( namespace );
                    }
                }
            }
            return answer;
        }
        else {
            if ( content instanceof Namespace ) {
                Namespace namespace = (Namespace) content;
                if ( ! defaultNamespaceURI.equals( namespace.getURI() ) ) {
                    return createSingleResultList( namespace );
                }
            }
        }
        return createEmptyList();
    }
    
    
    // Processing instruction API
    
    public List processingInstructions() {
        if ( content instanceof List ) {
            List list = (List) content;
            BackedList answer = createResultList();
            int size = list.size();
            for ( int i = 0; i < size; i++ ) {
                Object object = list.get(i);
                if ( object instanceof ProcessingInstruction ) {
                    answer.addLocal( object );
                }
            }
            return answer;
        }
        else {
            if ( content instanceof ProcessingInstruction ) {
                return createSingleResultList( content );
            }
            return createEmptyList();
        }
    }
    
    public List processingInstructions(String target) {
        if ( content instanceof List ) {
            List list = (List) content;
            BackedList answer = createResultList();
            int size = list.size();
            for ( int i = 0; i < size; i++ ) {
                Object object = list.get(i);
                if ( object instanceof ProcessingInstruction ) {
                    ProcessingInstruction pi = (ProcessingInstruction) object;
                    if ( target.equals( pi.getName() ) ) {                  
                        answer.addLocal( pi );
                    }
                }
            }
            return answer;
        }
        else {
            if ( content instanceof ProcessingInstruction ) {
                ProcessingInstruction pi = (ProcessingInstruction) content;
                if ( target.equals( pi.getName() ) ) {                  
                    return createSingleResultList( pi );
                }
            }
            return createEmptyList();
        }
    }
    
    public ProcessingInstruction processingInstruction(String target) {
        if ( content instanceof List ) {
            List list = (List) content;
            int size = list.size();
            for ( int i = 0; i < size; i++ ) {
                Object object = list.get(i);
                if ( object instanceof ProcessingInstruction ) {
                    ProcessingInstruction pi = (ProcessingInstruction) object;
                    if ( target.equals( pi.getName() ) ) {                  
                        return pi;
                    }
                }
            }
        }
        else {
            if ( content instanceof ProcessingInstruction ) {
                ProcessingInstruction pi = (ProcessingInstruction) content;
                if ( target.equals( pi.getName() ) ) {                  
                    return pi;
                }
            }
        }
        return null;
    }
    
    public boolean removeProcessingInstruction(String target) {
        if ( content instanceof List ) {
            List list = (List) content;
            for ( Iterator iter = list.iterator(); iter.hasNext(); ) {
                Object object = iter.next();
                if ( object instanceof ProcessingInstruction ) {
                    ProcessingInstruction pi = (ProcessingInstruction) object;
                    if ( target.equals( pi.getName() ) ) {                  
                        iter.remove();
                        return true;
                    }
                }
            }
        }
        else {
            if ( content instanceof ProcessingInstruction ) {
                ProcessingInstruction pi = (ProcessingInstruction) content;
                if ( target.equals( pi.getName() ) ) {                  
                    content = null;
                    return true;
                }
            }
        }
        return false;
    }
    
    public Element element(String name) {
        if ( content instanceof List ) {
            List list = (List) content;
            int size = list.size();
            for ( int i = 0; i < size; i++ ) {
                Object object = list.get(i);
                if ( object instanceof Element ) {
                    Element element = (Element) object;
                    if ( name.equals( element.getName() ) ) {
                        return element;
                    }
                }
            }
        }
        else {
            if ( content instanceof Element ) {
                Element element = (Element) content;
                if ( name.equals( element.getName() ) ) {
                    return element;
                }
            }
        }
        return null;
    }
    
    public Element element(QName qName) {
        if ( content instanceof List ) {
            List list = (List) content;
            int size = list.size();
            for ( int i = 0; i < size; i++ ) {
                Object object = list.get(i);
                if ( object instanceof Element ) {
                    Element element = (Element) object;
                    if ( qName.equals( element.getQName() ) ) {
                        return element;
                    }
                }
            }
        }
        else {
            if ( content instanceof Element ) {
                Element element = (Element) content;
                if ( qName.equals( element.getQName() ) ) {
                    return element;
                }
            }
        }
        return null;
    }

    public Element element(String name, Namespace namespace) {
        return element( getDocumentFactory().createQName( name, namespace ) );
    }
    
    
    
    public List elements() {
        if ( content instanceof List ) {
            List list = (List) content;
            BackedList answer = createResultList();
            int size = list.size();
            for ( int i = 0; i < size; i++ ) {
                Object object = list.get(i);
                if ( object instanceof Element ) {
                    answer.addLocal( object );
                }
            }
            return answer;
        }
        else {
            if ( content instanceof Element ) {
                Element element = (Element) content;
                return createSingleResultList( element );
            }
            return createEmptyList();
        }
    }
    
    public List elements(String name) {
        if ( content instanceof List ) {
            List list = (List) content;
            BackedList answer = createResultList();
            int size = list.size();
            for ( int i = 0; i < size; i++ ) {
                Object object = list.get(i);
                if ( object instanceof Element ) {
                    Element element = (Element) object;
                    if ( name.equals( element.getName() ) ) {
                        answer.addLocal( element );
                    }
                }
            }
            return answer;
        }
        else {
            if ( content instanceof Element ) {
                Element element = (Element) content;
                if ( name.equals( element.getName() ) ) {
                    return createSingleResultList( element );
                }
            }
            return createEmptyList();
        }
    }
    
    public List elements(QName qName) {
        if ( content instanceof List ) {
            List list = (List) content;
            BackedList answer = createResultList();
            int size = list.size();
            for ( int i = 0; i < size; i++ ) {
                Object object = list.get(i);
                if ( object instanceof Element ) {
                    Element element = (Element) object;
                    if ( qName.equals( element.getQName() ) ) {
                        answer.addLocal( element );
                    }
                }
            }
            return answer;
        }
        else {
            if ( content instanceof Element ) {
                Element element = (Element) content;
                if ( qName.equals( element.getQName() ) ) {
                    return createSingleResultList( element );
                }
            }
            return createEmptyList();
        }
    }
    
    public List elements(String name, Namespace namespace) {
        return elements( getDocumentFactory().createQName(name, namespace ) );
    }
    
    public Iterator elementIterator() {
        if ( content instanceof List ) {
            List list = (List) content;
            return new ElementIterator(list.iterator());
        }
        else {
            if ( content instanceof Element ) {
                Element element = (Element) content;
                return createSingleIterator( element );
            }
            return EMPTY_ITERATOR;
        }
    }
        
    public Iterator elementIterator(String name) {
        if ( content instanceof List ) {
            List list = (List) content;
            return new ElementNameIterator(list.iterator(), name);
        }
        else {
            if ( content instanceof Element ) {
                Element element = (Element) content;
                if ( name.equals( element.getName() ) ) {
                    return createSingleIterator( element );
                }
            }
            return EMPTY_ITERATOR;
        }
    }
    
    public Iterator elementIterator(QName qName) {
        if ( content instanceof List ) {
            List list = (List) content;
            return new ElementQNameIterator(list.iterator(), qName);
        }
        else {
            if ( content instanceof Element ) {
                Element element = (Element) content;
                if ( qName.equals( element.getQName() ) ) {
                    return createSingleIterator( element );
                }
            }
            return EMPTY_ITERATOR;
        }
    }
    
    public Iterator elementIterator(String name, Namespace namespace) {
        return elementIterator( getDocumentFactory().createQName( name, namespace ) );
    }
    
    public void setContent(List content) {
        this.content = content;
        if ( content instanceof ContentListFacade ) {
            this.content = ((ContentListFacade) content).getBackingList();
        }
    }
    
    public void clearContent() {
        content = null;
    }
    
    
    // node navigation API - return content as nodes
    // such as Text etc.
    public Node node(int index) {
        if ( index >= 0 ) {
            Object node = content;
            if ( content instanceof List ) {
                List list = (List) content;
                if ( index >= list.size() ) {
                    return null;
                }
                node = list.get(index);
            }
            if (node != null) {
                if (node instanceof Node) {
                    return (Node) node;
                }
                else {
                    return new DefaultText(node.toString());
                }
            }
        }
        return null;
    }
    
    public int indexOf(Node node) {
        if ( content instanceof List ) {
            List list = (List) content;
            return list.indexOf( node );
        }
        else {
            return ( content != null && content.equals( node ) ) 
                ? 0 : -1;
        }
    }
    
    public int nodeCount() {
        if ( content instanceof List ) {
            List list = (List) content;
            return list.size();
        }
        else {
            return ( content != null ) ? 1 : 0;
        }
    }
    
    public Iterator nodeIterator() {
        if ( content instanceof List ) {
            List list = (List) content;
            return list.iterator();
        }
        else {
            if ( content != null ) {
                return createSingleIterator( content );
            }
            else {
                return EMPTY_ITERATOR;
            }
        }
    }

    public List attributes() {
        return new ContentListFacade(this, attributeList());
    }
    
    public void setAttributes(List attributes) {
        this.attributes = attributes;
    }
    
    public Iterator attributeIterator() {
        if ( attributes == null ) {
            return EMPTY_ITERATOR;
        }
        else {
            return attributeList().iterator();
        }
    }
    
    public Attribute attribute(int index) {
        if ( attributes == null ) {
            return null;
        }
        else {
            return (Attribute) attributeList().get(index);
        }
    }
            
    public int attributeCount() {
        if ( attributes == null ) {
            return 0;
        }
        else {
            return attributeList().size();
        }
    }
    
    public Attribute attribute(String name) {
        if ( attributes != null ) {
            int size = attributes.size();
            for ( int i = 0; i < size; i++ ) {
                Attribute attribute = (Attribute) attributes.get(i);
                if ( name.equals( attribute.getName() ) ) {
                    return attribute;
                }
            }
        }
        return null;
    }

    public Attribute attribute(QName qName) {
        if ( attributes != null ) {
            int size = attributes.size();
            for ( int i = 0; i < size; i++ ) {
                Attribute attribute = (Attribute) attributes.get(i);
                if ( qName.equals( attribute.getQName() ) ) {
                    return attribute;
                }
            }
        }
        return null;
    }

    public Attribute attribute(String name, Namespace namespace) {
        return attribute( getDocumentFactory().createQName( name, namespace ) );
    }

    public boolean remove(Attribute attribute) {
        if ( attributes == null ) {
            return false;
        }
        boolean answer = attributes.remove(attribute);
        if ( answer ) {
            childRemoved(attribute);
        }
        return answer;
    }
    

    protected void addNode(Node node) {
        if (node.getParent() != null) {
            // XXX: could clone here
            String message = "The Node already has an existing parent of \"" 
                + node.getParent().getQualifiedName() + "\"";
            throw new IllegalAddException(this, node, message);
        }
        if (content == null) {
            content = node;
        }
        else {
            if (content instanceof List) {
                List list = (List) content;
                list.add( node );
            }
            else {
                List list = createContentList();
                list.add( content );
                list.add( node );
                content = list;
            }
        }
        childAdded(node);
    }

    protected boolean removeNode(Node node) {
        boolean answer = false;
        if (content != null) {
            if ( content == node ) {
                content = null;
                answer = true;
            }
            else if ( content instanceof List ) {
                List list = (List) content;
                answer = list.remove(node);
            }
        }
        if (answer) {
            childRemoved(node);
        }
        return answer;
    }

    // Implementation methods
    
    protected List getContentList() {
        if ( content instanceof List ) {
            return (List) content;
        }
        else {
            List list = createContentList();
            if ( content != null ) {
                list.add( content );
            }
            content = list;
            return list;
        }
    }

    protected List attributeList() {
        if ( attributes == null ) {
            attributes = createAttributeList();
        }
        return attributes;
    }
    
    protected void setAttributeList(List attributes) {
        this.attributes = attributes;
    }
    
    
    /** A Factory Method pattern which lazily creates 
      * a List implementation used to store content
      */
    protected List createContentList() {
        return new ArrayList();
    }
    
    /** A Factory Method pattern which lazily creates 
      * a List implementation used to store attributes
      */
    protected List createAttributeList() {
        return new ArrayList();
    }
    
    /** A Factory Method pattern which creates 
      * a BackedList implementation used to store results of 
      * a filtered content query such as 
      * {@link #processingInstructions} or
      * {@link #elements} which changes are reflected in the content
      */
    protected BackedList createResultList() {
        return new BackedList( this, getContentList() );
    }
    
    /** A Factory Method pattern which creates 
      * a BackedList implementation which contains a single result
      */
    protected List createSingleResultList( Object result ) {
        BackedList list = new BackedList( this, getContentList(), 1 );
        list.addLocal( result );
        return list;
    }
    
    /** A Factory Method pattern which lazily creates an empty
      * a BackedList implementation
      */
    protected List createEmptyList() {
        return new BackedList( this, getContentList(), 0 );
    }
    
    
    protected Iterator createSingleIterator( Object result ) {
        return new SingleIterator( result );
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
 * $Id: DefaultElement.java,v 1.27 2001/05/18 09:33:26 jstrachan Exp $
 */
