/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: DefaultXPath.java,v 1.11 2001/07/16 08:36:13 jstrachan Exp $
 */

package org.dom4j.xpath;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.InvalidXPathException;
import org.dom4j.Node;
import org.dom4j.VariableContext;
import org.dom4j.XPath;

import org.dom4j.rule.Pattern;

import org.dom4j.xpath.impl.Context;
import org.dom4j.xpath.impl.Expr;
import org.dom4j.xpath.impl.DefaultXPathFactory;

import org.saxpath.XPathReader;
import org.saxpath.SAXPathException;
import org.saxpath.helpers.XPathReaderFactory;

import org.jaxpath.JAXPathHandler;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/** <p>Main run-time interface into the XPath functionality</p>
 * 
 *  <p>The DefaultXPath object embodies a textual XPath as described by
 *  the W3C XPath specification.  It can be applied against a
 *  context node (or nodeset) along with context-helpers to
 *  produce the result of walking the XPath.</p>
 *
 *  <p>Example usage:</p>
 *
 *  <pre>
 *  <code>
 *
 *      // Create a new XPath
 *      DefaultXPath xpath = new DefaultXPath("a/b/c/../d/.[@name="foo"]);
 *
 *      // Apply the XPath to your root context.
 *      Object results = xpath.applyTo(myContext);
 *
 *  </code>
 *  </pre>
 *
 *
 *  @see org.dom4j.xpath.ContextSupport
 *  @see org.dom4j.xpath.NamespaceContext
 *  @see org.dom4j.VariableContext
 *  @see org.dom4j.xpath.FunctionContext
 *  @see org.dom4j.xpath.XPathFunctionContext
 *
 *  @author bob mcwhirter (bob @ werken.com)
 */
public class DefaultXPath implements org.dom4j.XPath {

    private String _xpath = "";
    private Expr _expr;
    private ContextSupport _contextSupport = new ContextSupport();

    /** Construct an XPath
     */
    public DefaultXPath(String xpath) throws InvalidXPathException {
        _xpath = xpath;
        parse();
    }

    public String toString() {
        return "[XPath: " + _xpath + " " + _expr + "]";
    }

    
    // XPath interface 
    
    /** Retrieve the textual XPath string used to initialize this Object
     *
     *  @return The XPath string
     */
    public String getText() {
        return _xpath;
    }

    public VariableContext getVariableContext() {
        return _contextSupport.getVariableContext();
    }
    
    public void setVariableContext(VariableContext variableContext) {
        _contextSupport.setVariableContext( variableContext );
    }
    
    public ContextSupport getContextSupport() {
        return _contextSupport;
    }
    
    public void setContextSupport(ContextSupport contextSupport) {
        _contextSupport = contextSupport;
    }
    
    public Object selectObject(Object context) {
        if ( context instanceof Node ) {
            return selectObject( (Node) context );
        }
        else if ( context instanceof List ) {
            return selectObject( (List) context );
        }
        return null;
    }
    
    public Object selectObject(Node node) {
        return _expr.evaluate( new Context( node, _contextSupport ) );
    }

    public Object selectObject(List nodes) {
        return _expr.evaluate( new Context( nodes, _contextSupport ) );
    }
    
    /** <p><code>selectNodes</code> performs this XPath expression
      * on the given {@link Node} or {@link List} of {@link Node}s 
      * instances appending all the results together into a single list.</p>
      *
      * @param context is either a node or a list of nodes on which to 
      *    evalute the XPath
      * @return the results of all the XPath evaluations as a single list
      */
    public List selectNodes(Object context) {
        return applyTo( context );
    }
    
    
    /** <p><code>selectNodes</code> evaluates the XPath expression
      * on the given {@link Node} or {@link List} of {@link Node}s 
      * and returns the result as a <code>List</code> of 
      * <code>Node</code>s sorted by the sort XPath expression.</p>
      *
      * @param context is either a node or a list of nodes on which to 
      *    evalute the XPath
      * @param sortXPath is the XPath expression to sort by
      * @return a list of <code>Node</code> instances 
      */
    public List selectNodes(Object context, org.dom4j.XPath sortXPath) {
        List answer = applyTo( context );
        sortXPath.sort( answer );
        return answer;
    }
    
    /** <p><code>selectNodes</code> evaluates the XPath expression
      * on the given {@link Node} or {@link List} of {@link Node}s 
      * and returns the result as a <code>List</code> of 
      * <code>Node</code>s sorted by the sort XPath expression.</p>
      *
      * @param context is either a node or a list of nodes on which to 
      *    evalute the XPath
      * @param sortXPath is the XPath expression to sort by
      * @param distinct specifies whether or not duplicate values of the 
      *     sort expression are allowed. If this parameter is true then only 
      *     distinct sort expressions values are included in the result
      * @return a list of <code>Node</code> instances 
      */
    public List selectNodes(Object context, org.dom4j.XPath sortXPath, boolean distinct) {
        List answer = applyTo( context );
        sortXPath.sort( answer, distinct );
        return answer;
    }
    
    
    /** <p><code>selectSingleNode</code> evaluates this XPath expression
      * on the given {@link Node} or {@link List} of {@link Node}s 
      * and returns the result as a single <code>Node</code> instance.</p>
      *
      * @param context is either a node or a list of nodes on which to 
      *    evalute the XPath
      * @return a single matching <code>Node</code> instance
      */
    public Node selectSingleNode(Object context) {
        Object object = selectObject( context );
        if ( object instanceof Node ) {
            return (Node) object;
        }
        else if ( object instanceof List) {
            List list = (List) object;
            if ( ! list.isEmpty() ) {
                return (Node) list.get(0);
            }
        }
        return null;
    }
    
    
    /** <p><code>valueOf</code> evaluates this XPath expression
      * and returns the textual representation of the results using the 
      * XPath string() function.</p>
      *
      * @param context is either a node or a list of nodes on which to 
      *    evalute the XPath
      * @return the string representation of the results of the XPath expression
      */
    public String valueOf(Object context) {
        if ( context instanceof Node )  {
            return valueOf( (Node) context );
        }
        else if ( context instanceof List ) {
            return valueOf( (List) context );
        }
        return "";
    }

    public Number numberValueOf(Object context) {
        Object object = selectObject( context );
        if ( object instanceof Number ) {
            return (Number) object;
        }
        return null;
    }
    
    /** <p><code>sort</code> sorts the given List of Nodes
      * using this XPath expression as a {@link Comparator}.</p>
      *
      * @param list is the list of Nodes to sort
      */
    public void sort( List list ) {
        sort( list, false );
    }
    
    /** <p><code>sort</code> sorts the given List of Nodes
      * using this XPath expression as a {@link Comparator} 
      * and optionally removing duplicates.</p>
      *
      * @param list is the list of Nodes to sort
      * @param distinct if true then duplicate values (using the sortXPath for 
      *     comparisions) will be removed from the List
      */
    public void sort( List list, boolean distinct ) {
        if ( list != null && ! list.isEmpty() )  {
            int size = list.size();
            HashMap sortValues = new HashMap( size );
            for ( int i = 0; i < size; i++ ) {
                Object object = list.get(i);
                if ( object instanceof Node ) {
                    Node node = (Node) object;
                    Object expression = getCompareValue(node);
                    sortValues.put(node, expression);
                }
            }
            sort( list, sortValues );

            if (distinct) {
                removeDuplicates( list, sortValues );
            }
        }
    }
    
    public boolean matches( Node node ) {
        Context context = new Context( node, _contextSupport );
        return _expr.matches( context, node );
    }
    
    /** Sorts the list based on the sortValues for each node
      */
    protected void sort( List list, final Map sortValues ) {
        Collections.sort(
            list,
            new Comparator() {
                public int compare(Object o1, Object o2) {
                    o1 = sortValues.get(o1);
                    o2 = sortValues.get(o2);
                    if ( o1 == o2 ) {
                        return 0;
                    }
                    else if ( o1 instanceof Comparable )  {
                        Comparable c1 = (Comparable) o1;
                        return c1.compareTo(o2);
                    }
                    else if ( o1 == null )  {
                        return 1;
                    }
                    else if ( o2 == null ) {
                        return -1;
                    }
                    else {
                        return o1.equals(o2) ? 0 : -1;
                    }
                }
            }
        );
    }

    // Implementation methods
    
    
    
    /** Removes items from the list which have duplicate values
      */
    protected void removeDuplicates( List list, Map sortValues ) {
        // remove distinct
        HashSet distinctValues = new HashSet();
        for ( Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object node = iter.next();
            Object value = sortValues.get(node);
            if ( distinctValues.contains( value ) ) {
                iter.remove();
            }
            else {
                distinctValues.add( value );
            }
        }
    }
    
    /** @return the node expression used for sorting comparisons
      */
    protected Object getCompareValue(Node node) {
        return valueOf( node );
    }
    
    
    
    private void parse() {
        try {
            XPathReader reader = XPathReaderFactory.createReader();
            
            JAXPathHandler handler = new JAXPathHandler();
            
            handler.setXPathFactory( new DefaultXPathFactory() );
            
            reader.setXPathHandler( handler );
            
            reader.parse( _xpath );
            
            org.jaxpath.expr.XPath xpath = handler.getXPath(true);
            _expr = (Expr) xpath.getRootExpr();
        }
        catch (SAXPathException e) {
            throw new InvalidXPathException( _xpath, e.getMessage() );
        }
        if ( _expr == null ) {
            throw new InvalidXPathException( _xpath );
        }
    }
    
    protected List applyTo(Object context) {
        if ( context instanceof Node ) {
            return applyTo( (Node) context );
        }
        else if ( context instanceof List ) {
            return applyTo( (List) context );
        }
        return Collections.EMPTY_LIST;
    }
    

    public List applyTo(Node node) {
        return asList( 
            _expr.evaluate( new Context( node, _contextSupport ) ) 
        );
    }

    public List applyTo(List nodes) {
        return asList( 
            _expr.evaluate( new Context( nodes, _contextSupport ) ) 
        );
    }
    
    /** Perform the string() function on the return values of an XPath
     */
    public String valueOf(Node node) {
        if ( _expr == null ) {
            return "";
        }
        return _expr.valueOf( new Context( node, _contextSupport ) );
    }

    public String valueOf(List nodes) {
        if ( _expr == null ) {
            return "";
        }
        return _expr.valueOf( new Context( nodes, _contextSupport ) );
    }

    
    /** A helper method to detect for non List return types */
    private final List asList(Object object)  {
        if ( object instanceof List )  {
            return (List) object;
        }
        else if ( object == null ) {
            return Collections.EMPTY_LIST;
        }
        ArrayList results = new ArrayList(1);
        results.add(object);
        return results;
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
 * $Id: DefaultXPath.java,v 1.11 2001/07/16 08:36:13 jstrachan Exp $
 */
