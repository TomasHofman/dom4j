/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: NameTestStep.java,v 1.9 2001/07/27 12:33:51 jstrachan Exp $
 */


package org.dom4j.xpath.impl;

import org.dom4j.xpath.ContextSupport;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.rule.Pattern;
import org.dom4j.xpath.impl.Context;

import org.saxpath.Axis;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

public class NameTestStep extends UnAbbrStep {
    
    private String _namespacePrefix;
    private String _localName;
    private boolean matchesAnyName = false;
    private boolean matchesAnyNamespace = false;
    
    
    public NameTestStep( 
        Axis axis, 
        String namespacePrefix, 
        String localName 
    ) {
        super(axis);
        _namespacePrefix = namespacePrefix;
        _localName = localName;
        matchesAnyName = "*".equals( _localName );
        matchesAnyNamespace = matchesAnyName || _namespacePrefix.equals( "*" );        
    }
    
    public List applyToSelf(Object node, ContextSupport support) {
        if ( node instanceof Element ) {
            Element element = (Element) node;
            List results = new ArrayList();
            
            if ( matchesAnyName || element.getName().equals(_localName) ) {
                if ( matchesAnyNamespace || matchesPrefix( element ) ) {
                    results.add( node );
                }
            }
            return results;
        }
        else if ( node instanceof Attribute ) {
            Attribute attribute = (Attribute) node;
            List results = new ArrayList();
           
            if ( matchesAnyName || attribute.getName().equals(_localName) ) {
                if ( matchesAnyNamespace || matchesPrefix( attribute ) ) {
                    results.add( node );
                }
            }
            return results;
        }
        return Collections.EMPTY_LIST;
    }
    
    public List applyToAttribute(Object node, ContextSupport support) {
        if ( node instanceof Element ) {
            if ( matchesAnyName ) {
                return attributes( (Element) node );
            }
            else {
                Element element = (Element) node;
                Attribute attr = null;
                
                if ( matchesAnyNamespace ) {
                    attr = element.attribute( _localName );
                }
                else {
                    Namespace namespace = element.getNamespaceForPrefix( _namespacePrefix );
                    if ( namespace == null ) {
                        System.out.println( "WARNING: couldn't find namespace for prefix: " + _namespacePrefix );
                        //attr = element.attribute( _localName );
                    }
                    else {
                        QName qName = QName.get( _localName, namespace );
                        attr = element.attribute( qName );
                    }
                }
                
                if ( attr != null ) {
                    if ( ! attr.supportsParent() ) {
                        attr = (Attribute) attr.asXPathResult(element);
                    }
                    List results = new ArrayList();
                    results.add( attr );
                    return results;
                }
            }
        }
        return Collections.EMPTY_LIST;
    }
    
    public List applyToNamespace(Object node, ContextSupport support) {
        if ( node instanceof Element ) {
            Element element = (Element) node;
            
            if ( matchesAnyName ) {
                return getNamespaces( element );
            }
            else {
                Namespace namespace = element.getNamespaceForPrefix( _localName );
                if ( namespace != null ) {
                    if ( ! namespace.supportsParent() ) {
                        namespace = (Namespace) namespace.asXPathResult( element );
                    }
                    List results = new ArrayList();
                    results.add( namespace );
                    return results;
                }
            }
        }
        return Collections.EMPTY_LIST;
    }
    
    public List applyToChild(Object node, ContextSupport support) {
        
        if ( node instanceof Document ) {
            Element child = ((Document) node).getRootElement();
            
            if ( matchesAnyName || child.getName().equals( _localName ) ) {
                if ( matchesAnyNamespace || matchesPrefix( child ) ) {
                    return Collections.singletonList( child );
                }
            }
        }
        else if ( node instanceof Element ) {
            Element element = (Element) node;                        
            if ( matchesAnyNamespace ) {
                Iterator iter = null;
                if ( matchesAnyName ) {
                    iter = element.elementIterator();
                }
                else {
                    iter = element.elementIterator( _localName );
                }
                ArrayList results = new ArrayList();
                while ( iter.hasNext() ) {
                    results.add( iter.next() );
                }
                return results;
            }
            else {
                ArrayList results = new ArrayList();
                for ( Iterator iter = element.elementIterator(); iter.hasNext(); ) {
                    Element childElement = (Element) iter.next();
                    if ( matchesAnyName || _localName.equals( childElement.getName() ) ) {
                        Namespace ns = childElement.getNamespaceForPrefix( _namespacePrefix );
                        if ( ns != null ) {
                            if ( matchesNamespaceURIs( ns.getURI(), childElement.getNamespaceURI() ) ) {
                                results.add( childElement );
                            }
                        }
                    }
                }
                return results;
            }            
/*            
            Namespace ns = null;
            if ( _namespacePrefix != null ) {
                ns = element.getNamespaceForPrefix( _namespacePrefix );
            }
        
            if ( matchesAnyName ) {                
                Iterator iter = element.elementIterator();
                if ( ns == null ) {
                    while ( iter.hasNext() ) {
                        results.add( iter.next() );
                    }
                }
                else {
                    while ( iter.hasNext() ) {
                        Element nodeChild = (Element) iter.next();                        
                        if ( ns.equals( nodeChild.getNamespace() ) ) {
                            results.add( nodeChild );
                        }
                    }
                }
            }
            else {
                Iterator iter = ( ns != null ) 
                    ? element.elementIterator( QName.get( _localName, ns ) )
                    : element.elementIterator( _localName );
                while ( iter.hasNext() ) {
                    results.add( iter.next() );
                }
            }
*/
        }
        return Collections.EMPTY_LIST;
    }

    // Pattern methods
    
    public boolean matches( Context context, Node node ) {
        boolean matches = false;
        if ( node instanceof Element ) {
            Element element = (Element) node;
            ContextSupport support = context.getContextSupport();
            
            if ( matchesAnyName || element.getName().equals(_localName) ) {
                matches = matchesAnyNamespace || matchesPrefix( element );
            }
        }
        else if ( node instanceof Attribute ) {
            Attribute attribute = (Attribute) node;
            ContextSupport support = context.getContextSupport();
            
            if ( matchesAnyName || attribute.getName().equals(_localName) ) {
                matches = matchesAnyNamespace || matchesPrefix( attribute );
            }
        }
        if ( matches ) {
            // evaluate the predicates
            return super.matches( context, node );
        }
        return false;
    }

    public double getPriority() {
        // If the pattern has the form of a QName preceded by a 
        // ChildOrAttributeAxisSpecifier or has the form 
        // processing-instruction(Literal) preceded by a 
        // ChildOrAttributeAxisSpecifier, then the priority is 0.
        
        // If the pattern has the form NCName:* preceded by a 
        // ChildOrAttributeAxisSpecifier, then the priority is -0.25.
        if ( ! matchesAnyName ) {
            return 0;
        }
        else if ( ! matchesAnyNamespace ) { 
            return -0.25;
        }
        return Pattern.DEFAULT_PRIORITY;
    }
    
    public String getMatchesNodeName() {
        if ( ! matchesAnyName && _localName != null ) {
            return _localName;
        }
        return null;
    }
    
    protected boolean matchesPrefix( Element element ) {
        String uri = element.getNamespaceURI();
        Namespace ns = element.getNamespaceForPrefix( _namespacePrefix );
        if ( ns != null ) {
            return uri.equals( ns.getURI() );
        }
        return false;
/*        
        String prefix = element.getNamespacePrefix();
        
        // XXXX: should we map the prefix to a URI and compare that?
        
        //System.out.println( "Matching prefix: " + _namespacePrefix + " to: " + prefix );
        
        if ( _namespacePrefix == null || _namespacePrefix.length() == 0 ) {
            return ( prefix == null || prefix.length() == 0 );
        }
        
        return _namespacePrefix.equals( prefix );
*/
    }
    
    protected boolean matchesPrefix( Attribute attribute ) {
        Element parent = attribute.getParent();
        if ( parent != null ) {
            String uri = attribute.getNamespaceURI();
            Namespace ns = parent.getNamespaceForPrefix( _namespacePrefix );
            if ( ns != null ) {
                return uri.equals( ns.getURI() );
            }
        }
        return false;
        
/*        
        String prefix = attribute.getNamespacePrefix();
        
        // XXXX: should we map the prefix to a URI and compare that?
        
        //System.out.println( "Matching prefix: " + _namespacePrefix + " to: " + prefix );
        
        if ( _namespacePrefix == null || _namespacePrefix.length() == 0 ) {
            return ( prefix == null || prefix.length() == 0 );
        }
        return _namespacePrefix.equals( prefix );
*/
    }

    /** @return true if the two namespace URIs are equal
     */
    protected boolean matchesNamespaceURIs( String u1, String u2 ) {
        if ( u1 == u2 ) {
            return true;
        }
        if ( u1 != null && u2 != null ) {
            return u1.equals( u2 );
        }
        return false;
    }
        
    public String toString() {
        return "[NameTestStep [ name: " + _namespacePrefix + ":" + _localName + " " + super.toString() + " ]]";
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
 * $Id: NameTestStep.java,v 1.9 2001/07/27 12:33:51 jstrachan Exp $
 */
