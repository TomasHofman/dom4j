/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: DefaultNamespace.java,v 1.6 2001/01/16 18:52:16 jstrachan Exp $
 */

package org.dom4j.tree;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;

/** <p><code>DefaultNamespace</code> is the DOM4J default implementation
  * of <code>Namespace</code>.</p>
  *
  * @version $Revision: 1.6 $
  */
public class DefaultNamespace extends AbstractNamespace {

    /** Cache of Namespace instances */
    protected static final NamespaceCache cache = new NamespaceCache();
    
    /** The prefix mapped to this namespace */
    private String prefix;

    /** The URI for this namespace */
    private String uri;

    /** A cached version of the hashcode for efficiency */
    private int hashCode;

    
    public static Namespace get(String prefix, String uri) {
        return cache.get(prefix, uri);
    }
    
    /** @param prefix is the prefix for this namespace
      * @param uri is the URI for this namespace
      */
    public DefaultNamespace(String prefix, String uri) {
        this.prefix = prefix;
        this.uri = uri;
    }

    
    /** @return the hash code based on the qualified name and the URI of the 
      * namespace.
      */
    public int hashCode() {
        if ( hashCode == 0 ) {
            hashCode = uri.hashCode() 
                ^ prefix.hashCode();
            if ( hashCode == 0 ) {
                hashCode = 0xbabe;
            }
        }
        return hashCode;
    }
  
    public boolean equals(Object object) {
        if ( this == object ) {
            return true;
        }
        else if ( object instanceof Namespace ) {
            Namespace that = (Namespace) object;
            
            // we cache hash codes so this should be quick
            if ( hashCode() == that.hashCode() ) {
                return uri.equals( that.getURI() ) 
                    && prefix.equals( that.getPrefix() );
            }
        }
        return false;
    }
    
    /** @return the prefix for this <code>Namespace</code>.
      */
    public String getPrefix() {
        return prefix;
    }

    /** @return the URI for this <code>Namespace</code>.
      */
    public String getURI() {
        return uri;
    }


    protected Node createXPathNode(Element parent) {
        return new XPathNamespace( parent, getPrefix(), getURI() );
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
 * $Id: DefaultNamespace.java,v 1.6 2001/01/16 18:52:16 jstrachan Exp $
 */
