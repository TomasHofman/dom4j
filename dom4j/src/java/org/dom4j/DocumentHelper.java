/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: DocumentHelper.java,v 1.19 2004/06/24 09:15:30 maartenc Exp $
 */

package org.dom4j;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.dom4j.io.SAXReader;
import org.dom4j.rule.Pattern;
import org.jaxen.VariableContext;

/** <p><code>DocumentHelper</code> is a collection of helper methods 
  * for using DOM4J.</p>
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 1.19 $
  */
public class DocumentHelper {

    
    // Static helper methods
    
    public static Document createDocument() {
        return DocumentFactory.getInstance().createDocument();
    }
    
    public static Document createDocument(Element rootElement) {
        return DocumentFactory.getInstance().createDocument(rootElement);
    }

    
    public static Element createElement(QName qname) {
        return DocumentFactory.getInstance().createElement(qname);
    }
    
    public static Element createElement(String name) {
        return DocumentFactory.getInstance().createElement(name);
    }
    
    
    public static Attribute createAttribute(Element owner, QName qname, String value) {
        return DocumentFactory.getInstance().createAttribute(owner, qname, value);
    }
    
    public static Attribute createAttribute(Element owner, String name, String value) {
        return DocumentFactory.getInstance().createAttribute(owner, name, value);
    }
    
    public static CDATA createCDATA(String text) {
        return DocumentFactory.getInstance().createCDATA(text);
    }
    
    public static Comment createComment(String text) {
        return DocumentFactory.getInstance().createComment(text);
    }
    
    public static Text createText(String text) {
        return DocumentFactory.getInstance().createText(text);
    }
    
    
    public static Entity createEntity(String name, String text) {
        return DocumentFactory.getInstance().createEntity(name, text);
    }
    
    public static Namespace createNamespace(String prefix, String uri) {
        return DocumentFactory.getInstance().createNamespace(prefix, uri);
    }
    
    public static ProcessingInstruction createProcessingInstruction(String target, String data) {
        return DocumentFactory.getInstance().createProcessingInstruction(target, data);
    }
    
    public static ProcessingInstruction createProcessingInstruction(String target, Map data) {
        return DocumentFactory.getInstance().createProcessingInstruction(target, data);
    }
    
    public static QName createQName(String localName, Namespace namespace) {
        return DocumentFactory.getInstance().createQName(localName, namespace);
    }
    
    public static QName createQName(String localName) {
        return DocumentFactory.getInstance().createQName(localName);
    }
    
    
    /** <p><code>createXPath</code> parses an XPath expression
      * and creates a new XPath <code>XPath</code> instance
      * using the singleton {@link DocumentFactory}.</p>
      *
      * @param xpathExpression is the XPath expression to create
      * @return a new <code>XPath</code> instance
      * @throws InvalidXPathException if the XPath expression is invalid
      */
    public static XPath createXPath(String xpathExpression) throws InvalidXPathException {
        return DocumentFactory.getInstance().createXPath(xpathExpression);
    }
    
    /** <p><code>createXPath</code> parses an XPath expression
      * and creates a new XPath <code>XPath</code> instance
      * using the singleton {@link DocumentFactory}.</p>
      *
      * @param xpathExpression is the XPath expression to create
      * @param variableContext is the variable context to use when evaluating the XPath
      * @return a new <code>XPath</code> instance
      * @throws InvalidXPathException if the XPath expression is invalid
      */
    public static XPath createXPath(String xpathExpression, VariableContext variableContext) throws InvalidXPathException {
        return DocumentFactory.getInstance().createXPath(xpathExpression, variableContext);
    }
    
    
    /** <p><code>createXPathFilter</code> parses a NodeFilter
      * from the given XPath filter expression using the singleton
      * {@link DocumentFactory}.
      * XPath filter expressions occur within XPath expressions such as
      * <code>self::node()[ filterExpression ]</code></p>
      *
      * @param xpathFilterExpression is the XPath filter expression 
      * to create
      * @return a new <code>NodeFilter</code> instance
      */
    public static NodeFilter createXPathFilter(String xpathFilterExpression) {
        return DocumentFactory.getInstance().createXPathFilter(xpathFilterExpression);
    }
    
    /** <p><code>createPattern</code> parses the given 
      * XPath expression to create an XSLT style {@link Pattern} instance
      * which can then be used in an XSLT processing model.</p>
      *
      * @param xpathPattern is the XPath pattern expression 
      * to create
      * @return a new <code>Pattern</code> instance
      */
    public static Pattern createPattern(String xpathPattern) {
        return DocumentFactory.getInstance().createPattern(xpathPattern);
    }
    
    
    /** <p><code>selectNodes</code> performs the given XPath
      * expression on the {@link List} of {@link Node} instances appending
      * all the results together into a single list.</p>
      *
      * @param xpathFilterExpression is the XPath filter expression 
      * to evaluate
      * @param nodes is the list of nodes on which to evalute the XPath
      * @return the results of all the XPath evaluations as a single list
      */
    public static List selectNodes(String xpathFilterExpression, List nodes) {
        XPath xpath = createXPath( xpathFilterExpression );
        return xpath.selectNodes( nodes );
    }
    
    /** <p><code>selectNodes</code> performs the given XPath
      * expression on the {@link List} of {@link Node} instances appending
      * all the results together into a single list.</p>
      *
      * @param xpathFilterExpression is the XPath filter expression 
      * to evaluate
      * @param node is the Node on which to evalute the XPath
      * @return the results of all the XPath evaluations as a single list
      */
    public static List selectNodes(String xpathFilterExpression, Node node) {
        XPath xpath = createXPath( xpathFilterExpression );
        return xpath.selectNodes( node );
    }
    
    /** <p><code>sort</code> sorts the given List of Nodes
      * using an XPath expression as a {@link Comparator}.
      *
      * @param list is the list of Nodes to sort
      * @param xpathExpression is the XPath expression used for comparison
      */
    public static void sort( List list, String xpathExpression ) {
        XPath xpath = createXPath( xpathExpression );
        xpath.sort( list );
    }
    
    /** <p><code>sort</code> sorts the given List of Nodes
      * using an XPath expression as a {@link Comparator}
      * and optionally removing duplicates.</p>
      *
      * @param list is the list of Nodes to sort
      * @param xpathExpression is the XPath expression used for comparison
      * @param distinct if true then duplicate values (using the sortXPath for 
      *     comparisions) will be removed from the List
      */
    public static void sort( List list, String xpathExpression, boolean distinct ) {
        XPath xpath = createXPath( xpathExpression );
        xpath.sort( list, distinct );
    }
    
    /** <p><code>parseText</code> parses the given text as an XML document
      * and returns the newly created Document.
      *
      * @param text is the XML text to be parsed
      * @return a newly parsed Document
      * @throws DocumentException if the document could not be parsed
      */
    public static Document parseText(String text) throws DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(new StringReader(text));
    }

    /** <p><code>parseText</code> parses the given text as an XML document
      * and returns the newly created Document, using the specified encoding.</p>
      *
      * @param text is the XML text to be parsed
      * @param encoding text encoding
      * @return a newly parsed Document
      * @throws DocumentException if the document could not be parsed
      * @throws UnsupportedEncodingException if the specified encoding is not 
      *         supported.
      */
    public static Document parseText(String text, String encoding) 
        throws DocumentException, UnsupportedEncodingException {
        SAXReader reader = new SAXReader();
        return reader.read(new ByteArrayInputStream(text.getBytes(encoding)));
    }

    /** <p>makeElement</p> a helper method which navigates from the
      * given Document or Element node to some Element using the path 
      * expression, creating any necessary elements along the way.
      * For example the path <code>a/b/c</code> would get the first
      * child &lt;a&gt; element, which would be created if it did not
      * exist, then the next child &lt;b&gt; and so on until finally a
      * &lt;c&gt; element is returned.
      *
      * @param source is the Element or Document to start navigating from
      * @param is a simple path expression, seperated by '/' which denotes
      * the path from the source to the resulting element such as a/b/c
      *
      * @return the first Element on the given path which either already
      * existed on the path or were created by this method.
      */
    public static Element makeElement(Branch source, String path) {
        StringTokenizer tokens = new StringTokenizer( path, "/" );
        Element parent;
        if ( source instanceof Document ) {
            Document document = (Document) source;
            parent = document.getRootElement();
            
            // lets throw a NoSuchElementException 
            // if we are given an empty path
            String name = tokens.nextToken();
            if ( parent == null ) {
                parent = document.addElement( name );
            }
        }
        else {
            parent = (Element) source;
        }
        Element element = null;
        while ( tokens.hasMoreTokens() ) {
            String name = tokens.nextToken();
            if ( name.indexOf( ':' ) > 0 ) {
                element = parent.element( parent.getQName( name ) );
            }
            else {
                element = parent.element( name );
            }
            if ( element == null ) {
                element = parent.addElement( name );
            }
            parent = element;
        }
        return element;
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
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * $Id: DocumentHelper.java,v 1.19 2004/06/24 09:15:30 maartenc Exp $
 */
