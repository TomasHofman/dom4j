/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: DefaultElement.java,v 1.5 2001/01/10 15:22:33 jstrachan Exp $
 */

package org.dom4j.tree;

import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.dom4j.IllegalAddNodeException;
import org.dom4j.Node;
import org.dom4j.Namespace;
import org.dom4j.ProcessingInstruction;
import org.dom4j.Text;

/** <p><code>DefaultElement</code> is the default DOM4J default implementation
  * of an XML element.</p>
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.5 $
  */
public class DefaultElement extends AbstractElement {

    /** The parent of this node */
    private Element parent;

    /** The document of this node */
    private Document document;

    /** The <code>NameModel</code> for this element */
    private NameModel nameModel;
    
    /** The <code>ContentModel</code> for this element */
    private ContentModel contentModel;
    
    /** The <code>AttributeModel</code> for this element */
    private AttributeModel attributeModel;

    
    public DefaultElement() { 
        this.nameModel = NameModel.EMPTY_NAME;
    }

    public DefaultElement(String name) { 
        this.nameModel = NameModel.get(name);
    }

    public DefaultElement(NameModel nameModel) { 
        this.nameModel = nameModel;
    }

    public DefaultElement(String name, Namespace namespace) { 
        this.nameModel = NameModel.get(name, namespace);
    }

    public void setNamespace(Namespace namespace) {
        this.nameModel = NameModel.get(getName(), namespace);
    }
    
    public void setName(String name) {
        this.nameModel = NameModel.get(name, getNamespace());
    }
    
    public Element getParent() {
        return parent;
    }

    public void setParent(Element parent) {
        this.parent = parent;
    }

    public Document getDocument() {
        if ( document != null ) {
            return document;
        }
        if ( parent != null ) {
            return parent.getDocument();
        }
        return null;
    }
    
    public void setDocument(Document document) {
        this.document = document;
    }
    
    public boolean supportsParent() {
        return true;
    }

    protected NameModel getNameModel() {
        return nameModel;
    }
    
    /** Allow derived classes to change the name model */
    protected void setNameModel(NameModel nameModel) {
        this.nameModel = nameModel;
    }
    
    
    /** Allows derived classes to override the content model */
    protected ContentModel getContentModel() {
        if ( contentModel == null ) {
            contentModel = createContentModel();
        }
        return contentModel;
    }
    
    /** Allow derived classes to set the <code>ContentModel</code>
      */
    protected void setContentModel(ContentModel contentModel) {
        this.contentModel = contentModel;
    }

    protected AttributeModel getAttributeModel() {
        if ( attributeModel == null ) {
            attributeModel = createAttributeModel();
        }
        return attributeModel;
    }

    /** Allow derived classes to set the <code>AttributeModel</code>
      */
    protected void setAttributeModel(AttributeModel attributeModel) {
        this.attributeModel = attributeModel;
    }


    /** A Factory Method pattern which lazily creates 
      * a ContentModel implementation 
      */
    protected ContentModel createContentModel() {
        return new DefaultContentModel();
    }
    
    /** A Factory Method pattern which lazily creates 
      * an AttributeModel implementation 
      */
    protected AttributeModel createAttributeModel() {
        return new DefaultAttributeModel();
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
 * $Id: DefaultElement.java,v 1.5 2001/01/10 15:22:33 jstrachan Exp $
 */
