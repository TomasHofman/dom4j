/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: BeanAttribute.java,v 1.1 2001/03/01 23:07:46 jstrachan Exp $
 */

package org.dom4j.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.tree.AbstractAttribute;

/** <p><code>BeanAttribute</code> represents a mutable XML attribute which
  * is backed by a property of the JavaBean of its parent element.</p>
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.1 $
  */
public class BeanAttribute extends AbstractAttribute {

    /** Empty arguments for reflection calls */
    protected static final Object[] NULL_ARGS = {};
    
    /** The <code>QName</code> for this element */
    private QName qname;
    
    /** The BeanElement that this */
    private BeanElement parent;

    /** The property this attribute represents */
    private PropertyDescriptor descriptor;
    
    
    public BeanAttribute(QName qname, BeanElement parent, PropertyDescriptor descriptor) {
        this.qname = qname;
        this.parent = parent;
        this.descriptor = descriptor;
    }

    public QName getQName() {
        return qname;
    }

    public Element getParent() {
        return parent;
    }
    
    public String getValue() {
        Object data = getData();
        return ( data != null ) ? data.toString() : null;
    }
    
    public Object getData() {
        try {
            Method method = descriptor.getReadMethod();
            return method.invoke( parent.getData(), NULL_ARGS );
        }
        catch (Exception e) {
            handleException(e);
            return null;
        }
    }
    
    public void setData(Object data) {
        try {
            Method method = descriptor.getWriteMethod();
            Object[] args = { data };
            method.invoke( parent.getData(), args );
        }
        catch (Exception e) {
            handleException(e);
        }
    }
    
    protected void handleException(Exception e) {
        // ignore introspection exceptions
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
 * $Id: BeanAttribute.java,v 1.1 2001/03/01 23:07:46 jstrachan Exp $
 */
