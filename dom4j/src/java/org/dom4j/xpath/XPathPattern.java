/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: XPathPattern.java,v 1.7 2001/08/01 09:08:39 jstrachan Exp $
 */

package org.dom4j.xpath;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.InvalidXPathException;
import org.dom4j.rule.Pattern;

import org.jaxen.BaseXPath;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** <p><code>XPathPattern</code> is an implementation of Pattern
  * which uses an XPath xpath.</p>
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 1.7 $
  */
public class XPathPattern implements Pattern {
    
    private String text;
    private BaseXPath xpath;

    
    public XPathPattern(String text) {
        this.text = text;
        this.xpath = DefaultXPath.parse( text );
    }

    public boolean matches( Node node ) {
        //return xpath != null && xpath.matches( getContext( node ), node );
        return false;
    }
    
    public String getText() {
        return text;
    }

    
    public double getPriority()  {
        return Pattern.DEFAULT_PRIORITY;
    }
    
    public Pattern[] getUnionPatterns() {
        return null;
    }

    public short getMatchType() {
        return ANY_NODE;
    }

    public String getMatchesNodeName() {
        return null;
    }
    
/*    
    public double getPriority()  {
        return ( xpath != null ) 
            ? xpath.getPriority() : Pattern.DEFAULT_PRIORITY;
        return Pattern.DEFAULT_PRIORITY
    }
    
    public Pattern[] getUnionPatterns() {
        return ( xpath != null ) 
            ? xpath.getUnionPatterns() : null;
    }

    public short getMatchType() {
        return ( xpath != null ) 
            ? xpath.getMatchType() : ANY_NODE;
    }

    public String getMatchesNodeName() {
        return ( xpath != null ) 
            ? xpath.getMatchesNodeName() : null;
    }
*/
    
    
    
    public String toString() {
        return "[XPathPattern: text: " + text + " XPath: " + xpath + "]";
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
 * $Id: XPathPattern.java,v 1.7 2001/08/01 09:08:39 jstrachan Exp $
 */
